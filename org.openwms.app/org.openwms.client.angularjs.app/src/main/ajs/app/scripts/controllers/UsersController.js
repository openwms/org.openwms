'use strict';
define([
  'app',
  'services/CoreService'
], function (app) {

  var usersController = function ($scope, $http, $modal, $upload, $base64, toaster, CoreService) {

    $scope.selectedUsers = [];

    var ModalInstanceCtrl = function ($scope, $modalInstance, data) {
      $scope.selUser = data.selUser;
      $scope.dialog = data.dialog;
      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };
      $scope.ok = function () {
        $modalInstance.close($scope.selUser);
      };
    };

    var UploadCtrl = function ($scope, $modalInstance, data) {
      $scope.selectedUsers = data.selectedUsers;
      $scope.uploadDialog = data.dialog;
      $scope.ok = function () {
        $modalInstance.close($scope.selectedUsers);
      };
      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };
      $scope.hasUploader = function (index) {
        return $scope.upload[index] != null;
      };
      $scope.abort = function (index) {
        $scope.upload[index].abort();
        $scope.upload[index] = null;
      };
      $scope.onFileSelect = function ($files) {
        $scope.selectedFiles = [];
        $scope.progress = 0;
        if ($scope.upload && $scope.upload.length > 0) {
          for (var i = 0; i < $scope.upload.length; i++) {
            if ($scope.upload[i] != null) {
              $scope.upload[i].abort();
            }
          }
        }
        $scope.upload = [];
        $scope.uploadResult = [];
        $scope.selectedFiles = $files;
        $scope.dataUrls = [];
        if ($files.length === 1) {
          var $file = $files[0];
          if (window.FileReader && $file.type.indexOf('image') > -1) {
            var fileReader = new FileReader();
            fileReader.readAsDataURL($file);
          }
          $scope.progress = -1;
        }
      };

      $scope.start = function () {
        $scope.progress = 0;
        var fileReader = new FileReader();
        fileReader.readAsDataURL($scope.selectedFiles[0]);
        fileReader.onload = function (e) {
          $scope.upload[0] = $upload.http({
            url: $scope.rootUrl + "/users/" + $scope.selectedUsers[0].id,
            method: 'PUT',
            headers: {'Content-Type': 'multipart/form-data'},
            data: e.target.result
          }).then(function (response) {
            $scope.uploadResult.push(response.data.result);
          }, null, function (evt) {
            // Math.min is to fix IE which reports 200% sometimes
            $scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
          });
        };
      };
    };

    $scope.decode = function (img) {
      return $base64.decode(img);
    };

    $scope.addUser = function () {
      var modalInstance = $modal.open({
        templateUrl: 'addUserDlg.html',
        controller: ModalInstanceCtrl,
        resolve: {
          data: function () {
            return {
              selUser: {
                userDetails: undefined
              }, dialog: {
                title: "Add a new User"
              }
            };
          }
        }
      });
      modalInstance.result.then(
        function (user) {
          CoreService.add("/users", $scope, user).then(
            function (addedEntity) {
              $scope.userEntities.push(addedEntity);
            }, function (e) {
              onError(e);
            }
          );
        }
      );
    };

    $scope.editUser = function () {
      var modalInstance = $modal.open({
        templateUrl: 'addUserDlg.html',
        controller: ModalInstanceCtrl,
        resolve: {
          data: function () {
            return {
              selUser: $scope.selectedUser(),
              dialog: {
                title: "Edit User"
              }
            };
          }
        }
      });
      modalInstance.result.then(
        function (user) {
          CoreService.save("/users", $scope, user).then(
            onSaved, function (e) {
              onError(e);
            }
          );
        }
      );
    };

    $scope.deleteUser = function () {
      if ($scope.selectedUsers === null || $scope.selectedUsers.length === 0) {
        return;
      }
      var param = "";
      angular.forEach($scope.selectedUsers, function (user) {
        param += user.username + ",";
      });
      CoreService.delete('/users/' + param, $scope).then(
        function () {
          $scope.loadUsers();
          onSuccess("OK", "Success", "Successfully deleted selected Users.");
        }, function (e) {
          onError(e);
        }
      );
    };

    $scope.saveUser = function () {
      $http.defaults.headers.put['Auth-Token'] = $scope.authToken;
      $http.put($scope.rootUrl + '/users', $scope.selectedUser).success(function (data, status, headers, config) {
        $scope.selectedUser = data;
        angular.forEach($scope.userEntities, function (user) {
          if (user.username === $scope.selectedUser.username) {
            user = $scope.selectedUser;
            toaster.pop('success', "", "User saved");
          }
        });
      });
    };

    $scope.loadUsers = function () {
      $scope.selectedUsers = [];
      CoreService.getAll("/users", $scope).then(
        function (users) {
          $scope.userEntities = users;
        }, function (e) {
          onError(e);
        }
      );
    };

    $scope.changePassword = function () {
      $http.get($scope.rootUrl + '/users').success(function (data, status, headers, config) {
        $scope.userEntities = data;
      });
    };

    $scope.changeImage = function () {
      var modalInstance = $modal.open({
        templateUrl: 'uploadDlg.html',
        controller: UploadCtrl,
        resolve: {
          data: function () {
            return {
              selectedUsers: $scope.selectedUsers,
              dialog: {
                title: "Upload an Image"
              }
            };
          }
        }
      });
      modalInstance.result.then(
        function (file) {

        }, function () {
          $scope.loadUsers();
        }
      );
    };

    $scope.onClickUserCard = function (index) {
      if ($scope.isSelected(index)) {
        // remove row from selection array
        var i = $scope.selectedUsers.indexOf($scope.userEntities[index]);
        $scope.selectedUsers.splice(i, 1);
      } else {
        // Not selected, so select this user
        $scope.selectedUsers.push($scope.userEntities[index]);
      }
    };

    $scope.selectedUser = function () {
      return $scope.selectedUsers[$scope.selectedUsers.length - 1];
    };

    $scope.isSelected = function (index) {
      return $scope.selectedUsers.indexOf($scope.userEntities[index]) > -1 ? true : false;
    };

    $scope.multipleSelected = function () {
      return $scope.selectedUsers.length > 1 ? true : false;
    };

    $scope.oneSelected = function () {
      return $scope.selectedUsers.length === 1 ? true : false;
    };

    var preLoad = function () {
      if ($scope.userEntities === undefined) {
        $scope.loadUsers();
      }
    }();

    var onSaved = function () {
      $scope.loadUsers();
      onSuccess("OK", "Success", "Saved successfully.");
    };

    var onError = function (e) {
      console.log(e);
      if (e.data === undefined) {
        toaster.pop("error", "Server Error");
      } else {
        toaster.pop("error", "Server Error", "[" + e.data.httpStatus + "] " + e.data.message);
      }
    };

    var onSuccess = function (code, header, text) {
      toaster.pop("success", header, "[" + code + "] " + text, 2000);
    };
  };

  app.register.controller('UsersController', ['$scope', '$http', '$modal', '$upload', '$base64', 'toaster', 'CoreService', usersController]);
});




