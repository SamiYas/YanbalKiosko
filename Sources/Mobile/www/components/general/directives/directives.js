angular.module('YanbalKiosko.directives', [])

.directive('customWidthDirective', [ '$timeout', function ($timeout) {
    return {
        restrict: 'AC',
        link: function ($scope, element) {
            $timeout(function () {
                var textElem = element.find('i')[2];
                element[0].setAttribute('style','display: block !important');
                var rightSpace = textElem.offsetWidth + 28;
                element[0].setAttribute('style', '');
                if ($scope.parentTitle) {
                    rightSpace = rightSpace - 2;
                }
                if ($scope.item.NUEVO) {
                    element.find('i')[1].style.right = rightSpace + 'px';
                } else {
                    element.find('i')[1].style.right = '22px';
                }
            }, 0);
            $scope.$on('items.refresh', function () {
                $timeout(function () {
                    var textElem = element.find('i')[2];
                    element[0].setAttribute('style', 'display: block !important');
                    var rightSpace = textElem.offsetWidth + 28;
                    element[0].setAttribute('style', '');
                    if ($scope.parentTitle) {
                        rightSpace = rightSpace - 2;
                    }
                    if ($scope.item.NUEVO) {
                        element.find('i')[1].style.right = rightSpace + 'px';
                    } else {
                        element.find('i')[1].style.right = '22px';
                    }
                }, 0);
            });
        }
    };
}])

.directive('dropdownDirective', ['$timeout', function ($timeout) {
    return {
        restrict: 'AC',
        link: function ($scope, element) {
            element.bind('focus', function () {
                //agregar un timeout por si existe algun listener esperando el evento
                $timeout(function () {
                    document.getElementsByClassName('yanbal-country-dropdown')[0].style.display = 'block';
                }, 0, false);
            })
           .bind('blur', function () {
               $timeout(function () {
                   document.getElementsByClassName('yanbal-country-dropdown')[0].style.display = 'none';
               }, 0, false);
           });
        }
    };
}])

.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                document.getElementById("loginButton").focus();
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter);
                });
                event.preventDefault();
            }
        });
    };
})

.directive('ngTouchstart', function () {
    return {
        controller: ['$scope', '$element', '$attrs', function ($scope, $element) {
            $element.bind('touchstart', onTouchStart);

            function onTouchStart() {
                var method = '$scope.' + $element.attr('ng-touchstart');
                $scope.$apply(function () {
                    $scope.$eval(method);// jshint ignore:line
                });
            }
        }]
    };
})

.directive('resetField', ['$timeout', function ($timeout) {
    return {
        require: "ngModel",
        restrict: 'A',
        link: function (scope, element) {
            element.bind('propertychange paste drop blur change input', function () {
                $timeout(function () {
                    if (element[0].value.length > 0) {
                        element.parent().next().css('display', 'block');
                    } else {
                        element.parent().next().css('display', 'none');
                    }
                }, 0);
            }).bind('focus', function () {
                //agregar un timeout por si existe algun listener esperando el evento
                $timeout(function () {
                    if (element[0].value.length > 0) {
                        element.parent().next().css('display', 'block');
                    } else {
                        element.parent().next().css('display', 'none');
                    }
                }, 0, false);
            }).bind('blur', function () {
                $timeout(function () {
                    element.parent().next().css('display', 'none');
                }, 0, false);
            });
        }
    };
}])

.directive('loadingDirective', ['$compile', function ($compile) {
    'use strict';
    var loadingTemplate = '<div class="loading-directive"><i class="ion-refresher-yanbal" /></div>';
    var _linker = function (scope, element) {
        element.html(loadingTemplate);
        $compile(element.contents())(scope);
        scope.$on('loading.hide', function () {
            element.addClass('closing');
        });
        scope.$on('loading.show', function () {
            element.removeClass('closing');
        });
    };
    return {
        restrict: 'E',
        link: _linker,
        scope: {
            content: '='
        }
    };
}])
.directive('validarUsername', ['$timeout', function ($timeout) {
    return {
        require: "ngModel",
        restrict: 'A',
        link: function (scope, element, attrs, ngModel) {
            element.bind('propertychange paste drop blur change input', function () {
                $timeout(function () {
                    var texto = element[0].value;
                    var filtrado = texto.replace(/[^A-Za-z0-9\s]+/g, '');
                    filtrado = filtrado.replace(/\x20/g, '');
                    var resultado = true;
                    if (filtrado.length > 0) {
                        resultado = false;
                    }
                    if (filtrado.length > 30) {
                        filtrado = filtrado.substring(0, 30);
                    }
                    if (texto.length !== filtrado.length) {
                        element[0].value = filtrado;
                        // modificar model
                        ngModel.$setViewValue(filtrado);
                    }
                    return resultado;
                }, 100);
            });
        }
    };
}])
.directive('validarPassword', ['$timeout', function ($timeout) {
    return {
        require: "ngModel",
        restrict: 'A',
        link: function (scope, element, attrs, ngModel) {
            element.bind('propertychange paste drop blur change input', function () {
                $timeout(function () {
                    var texto = element[0].value;
                    var filtrado = texto.replace(/[^A-Za-z0-9\x2D\x5F\x5C\x2F\x5E\x24\x2A\x2B\x3F\x21\x25\x26\x2E\x3A\x2C\x3D\x40\x23\x28\x29\x5B\x5D\x7B\x7D\s]+/g, '');
                    filtrado = filtrado.replace(/\x20/g, '');
                    var resultado = true;
                    if (filtrado.length > 0) {
                        resultado = false;
                    }
                    if (filtrado.length > 30) {
                        filtrado = filtrado.substring(0, 30);
                    }
                    if (texto.length !== filtrado.length) {
                        element[0].value = filtrado;
                        // modificar model
                        ngModel.$setViewValue(filtrado);
                    }
                    return resultado;
                }, 100);
            });
        }
    };
}]);