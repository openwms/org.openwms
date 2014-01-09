'use strict';

describe('Controller: PreferencesCtrl', function () {

  // load the controller's module
  beforeEach(module('openwms-root'));

  var PreferencesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    PreferencesCtrl = $controller('PreferencesCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
