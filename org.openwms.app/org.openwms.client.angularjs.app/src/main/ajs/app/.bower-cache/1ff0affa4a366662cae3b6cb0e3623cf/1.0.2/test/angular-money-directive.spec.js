'use strict';

describe('angular-money-directive', function () {
  var $compile, scope, inputEl, form;

  // https://github.com/angular/angular.js/blob/master/test/ng/directive/inputSpec.js
  function setValue(val) {
    form.price.$setViewValue(val);
    scope.$digest();
  }

  function setupDirective(attrs) {
    attrs = attrs || '';

    var formEl = angular.element(
      '<form name="form">' +
      '  <input name="price" ng-model="model.price" money ' + attrs + '>' +
      '</form>');
    $compile(formEl)(scope);

    scope.$digest();

    form = scope.form;
    inputEl = formEl.find('input')
  }


  beforeEach(module('fiestah.money'));
  beforeEach(inject(function (_$compile_, $rootScope) {
    $compile = _$compile_;
    scope = $rootScope.$new();
    scope.model = {};
  }));

  describe('when ngModel is undefined', function () {
    beforeEach(function () {
      setupDirective();
      setValue(undefined);
    });

    it('displays an empty string', function () {
      expect(inputEl.val()).to.equal('');
      expect(form.price.$valid).to.be.true;
    });
  });

  describe('when min = 0 (default)', function () {
    beforeEach(function () {
      setupDirective();
    });

    it('displays an empty string in the view by default', function () {
      expect(inputEl.val()).to.equal('');
    })

    it('accepts in-range values', function () {
      setValue('50.4');
      expect(scope.model.price).to.equal(50.4);
      expect(form.price.$valid).to.be.true;
    });

    it('accepts decimals without a leading zero', function () {
      setValue('.5');
      expect(scope.model.price).to.equal(0.5);
      expect(form.price.$valid).to.be.true;
    });

    it('rounds off to two decimal points', function () {
      setValue('41.999');
      expect(scope.model.price).to.equal(42);
    });

    it('disallows negative values', function () {
      setValue('-5');
      expect(scope.model.price).to.not.be.ok;
      expect(inputEl.val()).to.equal('');
      expect(form.price.$valid).to.be.true;
    });

    it('strips out invalid chars', function () {
      setValue('a');
      expect(scope.model.price).to.not.be.ok;
      expect(inputEl.val()).to.equal('');
    });

    it('reverts to the last valid value on invalid char', function () {
      // A valid value is first entered
      setValue('50.4');

      // Then "a" is entered next
      setValue('50.4a');

      expect(scope.model.price).to.equal(50.4);
      expect(inputEl.val()).to.equal('50.4');
      expect(form.price.$valid).to.be.true;
    });

    it('formats decimals on blur', function () {
      setValue('50');
      inputEl.triggerHandler('blur');
      expect(inputEl.val()).to.equal('50.00');
    });
  });

  describe('when min < 0', function () {
    beforeEach(function () {
      setupDirective('min="-10"');
    });

    it('allows the negative sign', function () {
      setValue('-');
      expect(scope.model.price).to.not.be.ok;
      expect(form.price.$valid).to.be.true;
    });
    it('allows negative values', function () {
      setValue('-5.4');
      expect(scope.model.price).to.equal(-5.4);
      expect(form.price.$valid).to.be.true;
    });
  });

  describe('when value > max', function () {
    it('marks it as invalid', function () {
      setupDirective('max="100"');
      setValue('100.5');
      expect(form.price.$invalid).to.be.true;
    });
  });

  describe('when precision = 0', function () {
    it('should round to int', function () {
      setupDirective('precision="0"');
      setValue('42.01');
      expect(scope.model.price).to.equal(42);
    });
  });

  describe('when precision = -1', function () {
    it('should disable rounding', function () {
      setupDirective('precision="-1"');
      setValue('41.999');
      expect(scope.model.price).to.equal(41.999);
    });
  });

});
