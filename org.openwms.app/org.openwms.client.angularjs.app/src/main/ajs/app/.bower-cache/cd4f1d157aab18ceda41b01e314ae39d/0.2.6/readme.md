# Angular PDF Viewer (0.2.6)

An AngularJS directive to display PDFs. [DEMO](http://codepen.io/winkerVSbecks/full/50010e383d0f80deab97858571400d86/)

## Dependencies

1. [AngularJS](http://angularjs.org/)
1. [PDFJS](http://mozilla.github.io/pdf.js/)


## Usage

1. `bower install angular-pdf-viewer`

2. Include the path to the lib, AngularJS and PDFJS:

```
<script src="bower_components/pdfjs-dist/build/pdf.js"></script>
<script src="bower_components/angular/angular.js"></script>
<script src="bower_components/angular-pdf-viewer/dist/angular-pdf-viewer.min.js"></script>
```

3. Include the lib as a dependency in your angular app:

``` js
var app = angular.module('App', ['pdf']);
```


## Directive

The URL, request headers, scale and delegate-handle can be set using the attributes:

``` html
<pdf-viewer
    delegate-handle="my-pdf-container"
    url="pdfUrl"
    scale="1"
    show-toolbar="true"
    headers="{ 'x-you-know-whats-awesome': 'EVERYTHING' }"></pdf-viewer>
```


## Delegate Service (pdfDelegate)

The pdfDelegate service allows you to access and control individual instances of a directive. This allows us to have multiple instances of the same directive in the same controller.

Inject the `pdfDelegate` service into your controller. You can then fetch an instance using it's delegate handle and call methods on it:

``` js
pdfDelegate.$getByHandle('my-pdf-container').zoomIn();
```

The following methods are available to the delegate:
- prev
- next
- zoomIn(amount) *default amount = 0.2*
- zoomOut(amount) *default amount = 0.2*
- zoomTo(amount)
- rotate *(clockwise by 90 degrees)*
- getPageCount
- getCurrentPage
- goToPage(pageNumber)
- load


## Change the PDF File

In order to replace the active PDF with another one, you can call the `load` method of the delegate. For example:

``` js
pdfDelegate
    .$getByHandle('my-pdf-container')
    .load('url-of-the-new-file.pdf');
```


## Example

Run `npm install && bower install` to install all dependencies. And then `gulp dev` to start a local server. The example will now be available at [localhost:3000/src](http://localhost:3000/src)


## Toolbar
The default toolbar can be shown or hidden using the `show-toolbar` attribute. Since the PDF can be easily controlled using the delegate service it's quite trivial to build a custom toolbar. Or place the toolbar on a separate scope.


## Similar projects

1. [angularjs-pdf](https://github.com/sayanee/angularjs-pdf)
2. [ng-pdfviewer](https://github.com/akrennmair/ng-pdfviewer)


## Credit

PDF examples used are [Relativity: The Special and General Theory by Albert Einstein](http://www.gutenberg.org/ebooks/30155) as kindly organized and made available free by [Project Gutenberg](http://www.gutenberg.org/wiki/Main_Page). And the [This is Material Design](http://static.googleusercontent.com/media/www.google.com/en//design/material-design.pdf) by Google.
