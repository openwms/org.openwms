'use strict';

describe('Controller: ModulesCtrl', function () {

  // load the controller's module
  beforeEach(module('openwms-root'));

  var ModulesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ModulesCtrl = $controller('ModulesCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
