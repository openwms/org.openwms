angular-minicolors
==================

## General

My first try of wrtiting a wrapper-directive around JQuery MiniColors by [Cory LaViska ](https://github.com/claviska) [https://github.com/claviska/jquery-minicolors](https://github.com/claviska/jquery-minicolors)

Works with Bootstrap 3 and works fine with mobile browsers such as Safari on iPad.

##[DEMO and API](https://kaihenzler.github.io/angular-minicolors)

## How To Install

1. Install by typing `bower install angular-minicolors` consider using the `--save` option to save the dependency to your own bower.json file

## How To Use

1. Include the JQuery MiniColors Files from the bower_components folder (bower_components/jquery-minicolors/) in your project.
The files you need are: `jquery-minicolors.js` `jquery-minicolors.css` and `jquery-minicolors.png` and of course JQuery itself

2. Add the dependency to your app definition `angular.module('myApp', ['minicolors'])`

3. Append `minicolors` attribute to any input-field. If you want to pass in a settings object, do it like this: `minicolors="MySettingsObject"`

angular-minicolors is planned to be API compatible with: [http://labs.abeautifulsite.net/jquery-minicolors/](http://labs.abeautifulsite.net/jquery-minicolors/)

keep in mind, that this is my first public angular-directive and it is by far not finished.

## default config

the default config is as follows:

```js
  theme: 'bootstrap',
  position: 'top left',
  defaultValue: '',
  animationSpeed: 50,
  animationEasing: 'swing',
  change: null,
  changeDelay: 0,
  control: 'hue',
  hide: null,
  hideSpeed: 100,
  inline: false,
  letterCase: 'lowercase',
  opacity: false,
  show: null,
  showSpeed: 100
```


## app-wide config

a Provider is now exposed and you can edit the global config like this:

```js
angular.module('my-app').config(function (minicolorsProvider) {
  angular.extend(minicolorsProvider.defaults, {
    control: 'hue',
    position: 'top left'
  });
});
```

## TODO

- wrap the original events in angular events
- add protection against false color values

## Found an issue?

Please report the issue and feel free to submit a pull request

## Copyright and license

The MIT License (MIT)

Copyright (c) 2013 Kai Henzler

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.