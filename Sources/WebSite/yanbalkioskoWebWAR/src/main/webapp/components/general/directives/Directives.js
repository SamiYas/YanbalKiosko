angular.module('app.directives', [])

.directive('selectizeDropdownColor', ['$timeout', function($timeout) {
    return {
        link: function(scope, element) {
            var placeholder = 'Seleccione color';
            var dropdown = function() {
                $(element).selectize({
                    allowEmptyOption: false,
                    render: {
                        item: function(item) {
                            var itemValue = '<div class="item"><div class="item-color">';
                            itemValue += '<div style="background-color:' + item.value;
                            itemValue += '"></div></div>' + item.text + '</div>';
                            return itemValue;

                        },
                        option: function(item) {
                            var optionValue = '<div class="option"><div class="item-color">';
                            optionValue += '<div style="background-color:' + item.value;
                            optionValue += '"></div></div>' + item.text + '</div>';
                            return optionValue;
                        }
                    },
                    onDropdownOpen: function() {
                        var inputText = $(element.parent())
                        .find('input[type=text]');
                        inputText.attr('placeholder', '');
                        inputText.prop('disabled', true);
                        inputText.placeholder();
                        inputText[0].value = '';
                    },
                    onDropdownClose: function() {
                        var inputText = $(element.parent())
                        .find('input[type=text]');
                        inputText.attr('placeholder', placeholder);
                        inputText.css('width', '108px');
                        inputText.placeholder();
                        inputText[0].value = placeholder;
                    },
                    onChange: function() {
                        placeholder = '';
                    }
                });
            };

            scope.$watch('colores', function(newValue, oldValue) {
                if (undefined !== newValue && undefined !== oldValue && newValue[0]) {
                    $timeout(dropdown, 0);
                }
            });
        }
    };
}])

.directive('selectizeDropdown', ['$timeout', function($timeout) {
    return {
        link: function(scope, element) {
            var dropdown = function() {
                $(element).selectize({
                    allowEmptyOption: true,
                    onFocus: function() {
                        $(element.parent()).find('input[type=text]')
                        .prop('disabled', true);
                    },
                    placeholder : ''
                });
            };

            scope.$watch('nroOrden', function(newValue, oldValue) {
                if (newValue && undefined !== oldValue && newValue[0]) {
                    $timeout(dropdown, 0);
                }
            });

            scope.$watch('nroOrdenArchivo', function(newValue, oldValue) {
                if (newValue && undefined !== oldValue && newValue[0]) {
                    $timeout(dropdown, 0);
                }
            });
        }
    };
}])

.directive('selectizeDropdownPais', ['$timeout', function($timeout) {
    return {
        require: 'ngModel',
        link: function(scope, element) {
            var dropdown = function() {
                var placeholder = 'Pa\u00EDs';
                $(element).selectize({
                    allowEmptyOption: false,
                    onDropdownOpen: function() {
                        var inputText = $(element.parent())
                        .find('input[type=text]');
                        inputText.attr('placeholder', '');
                        inputText.prop('disabled', true);
                        inputText.placeholder();
                        inputText[0].value = '';
                    },
                    onDropdownClose: function() {
                        var inputText = $(element.parent())
                        .find('input[type=text]');
                        inputText.attr('placeholder', placeholder);
                        inputText.placeholder();
                        inputText[0].value = placeholder;
                    },
                    onChange: function() {
                        placeholder = '';
                    }
                });
            };

            scope.$watch('paises', function(newValue, oldValue) {
                if (undefined !== newValue && undefined !== oldValue && newValue[0]) {
                    $timeout(dropdown, 0);
                }
            });

        }
    };
}])

.directive('selectizeDropdownNav', ['$timeout', function($timeout) {
    return {
        link: function(scope, element) {
            var dropdown = function() {
                $(element).selectize({
                    allowEmptyOption: true
                });
            };

            scope.$watch('paisesNav', function(newValue, oldValue) {
                if (newValue && undefined !== oldValue && newValue[0]) {
                    $timeout(dropdown, 0);
                }
            });
        }
    };
}])

.directive('resizeHeight', ['$state', function($state) {
    return {
        link: function(scope, element) {
            scope.resize = function() {
                var minHeight = 0;
                if ($state.current.name === 'login') {
                     minHeight = $('.container-fluid').first().height() - 50;
                } else {
                     minHeight = $('.container-fluid').first().height() - 220;
                }
                $(element).css('min-height', minHeight);
            };
            scope.resize();
            $(window).resize(function() {
                scope.resize();
            });
        }
    };
}])

.directive('validarUsername', ['$timeout', function($timeout) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {
            element.bind('propertychange paste drop blur change input',
                function() {
                    $timeout(function() {
                        var texto = $(element).val();
                        var filtrado = texto.replace(/[^A-Za-z0-9]+/g, '');
                        var resultado = true;
                        if (filtrado.length > 0) {
                            resultado = false;
                        }
                        if (texto.length !== filtrado.length) {
                            $(element).val(filtrado);
                            // modificar model
                            ngModel.$setViewValue(filtrado);
                        }
                        return resultado;
                }, 100);
            });

            element.bind('keypress', function(e) {
                var k = e.charCode || e.keyCode || 0;
                return ((k >= 48 && k <= 57) || //0-9
                        (k >= 65 && k <= 90) || (k >= 97 && k <= 122) ||//a-zA-Z
                        k === 8 || k === 9 || k === 16); // backspace, tab, shift
            });
        }
    };
}])

.directive('validarAlfanumerico', ['$timeout', function($timeout) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {
            element.bind('propertychange paste drop blur change input', function() {
                $timeout(function() {
                    var texto = $(element).val();
                    var filtrado = texto.replace(/[^A-Za-z0-9\x2E\x2C\x2D\x23\s\xE1\xE9\xED\xF3\xFA\xC1\xC9\xCD\xD3\xDA\xF1\xD1]+/g, '');
                    var resultado = true;
                    if (filtrado.length > 0) {
                        resultado = false;
                        // verificar maxlength (IE9)
                        var maxlength = $(element).attr('maxlength');
                        if (!!maxlength && filtrado.length > maxlength) {
                            filtrado = filtrado.substring(0, maxlength);
                        }
                    }
                    if (texto.length !== filtrado.length) {
                        $(element).val(filtrado);
                        // modificar model
                        ngModel.$setViewValue(filtrado);
                    }
                    return resultado;
                }, 100);
            });

            element.bind('keypress', function(e) {
                var k = e.charCode || e.keyCode || 0;
                return ((k >= 48 && k <= 57) || //0-9
                        (k >= 65 && k <= 90) || (k >= 97 && k <= 122) ||
                        k === 209 || k === 241 ||// a-zA-Z, ñÑ
                        k === 193 || k === 225 || k === 201 || k === 233 ||
                        k === 205 || k === 237 ||
                        k === 211 || k === 243 || k === 218 || k === 250 ||
                        k === 46 || k === 44 || k === 45 || k === 35 ||// .,-#
                        k === 8 || k === 9 || k === 16 || k === 32);
                        // backspace, tab, shift, space
            });
        }
    };
}])

.directive('fakeTextInput', ['$timeout', function($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element) {
            var realInput = $($(element.parent()).find('input')[1]);
            element.bind('focus', function() {
                $timeout(function() {
                    $(element).hide();
                    realInput.show().focus();
                }, 0);
            });
            realInput.bind('blur', function() {
                $timeout(function() {
                    if (!realInput.val().length) {
                        realInput.hide();
                        element.show();
                    }
                }, 0);
            });
        }
    };
}])

.directive('validarPassword', ['$timeout', function($timeout) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {

            element.bind('propertychange paste drop blur change input',
             function() {
                $timeout(function() {
                    var texto = $(element).val();
                    var filtrado = texto.replace(/[^A-Za-z0-9\x2D\x5F\x5C\x2F\x5E\x24\x2A\x2B\x3F\x21\x25\x26\x2E\x3A\x2C\x3D\x40\x23\x28\x29\x7C\x5B\x5D\x7B\x7D]+/g, '');
                    var resultado = true;
                    if (filtrado.length > 0) {
                        resultado = false;
                    }
                    if (texto.length !== filtrado.length) {
                        $(element).val(filtrado);
                        // modificar model
                        ngModel.$setViewValue(filtrado);
                    }
                    return resultado;
                }, 100);
            });

            element.bind('keypress', function(e) {
                var k = e.charCode || e.keyCode || 0;
                return ((k >= 48 && k <= 57) || //0-9
                        (k >= 65 && k <= 90) ||
                        (k >= 97 && k <= 122) || // a-zA-Z
                        // -_\/\\^$*+?!%&.:,=@#()|[\]{}
                        k === 45 || k === 95 || k === 92 || k === 47 ||
                        k === 94 || k === 36 || k === 42 || k === 43 ||
                        k === 63 || k === 33 || k === 37 || k === 38 ||
                        k === 46 || k === 58 || k === 44 || k === 61 ||
                        k === 64 || k === 35 || k === 40 || k === 41 ||
                        k === 124 || k === 91 || k === 93 ||
                        k === 123 || k === 125 ||
                        k === 8 || k === 9 || k === 16);
                        // backspace, tab, shift
            });

        }
    };
}])

.directive('validarDecimal', ['$timeout', function($timeout) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {
            element.bind('propertychange paste drop blur change input',
            function() {
                $timeout(function() {
                    var texto = $(element).val();
                    var filtrado = texto.replace(/[^0-9\x2E]+/g, '');
                    var resultado = true;
                    if (filtrado.length > 0) {
                        resultado = false;
                    }
                    if (texto.length !== filtrado.length) {
                        $(element).val(filtrado);
                        // modificar model
                        ngModel.$setViewValue(filtrado);
                    }
                    return resultado;
                }, 100);
            });

            element.bind('keyup mouseup mousedown mousemove',
            function() {
                var currentNumber = element.val();
                var pointIndex = currentNumber.indexOf('.');
                var hasDecimalPlace = pointIndex >= 0 && pointIndex < currentNumber.length - 2;

                if (isNaN(currentNumber) || hasDecimalPlace) {
                    element.val(currentNumber.substr(0, pointIndex + 2));
                    return false;
                }
            });

            element.bind('keypress', function(e) {
                var k = e.charCode || e.keyCode || 0;
                return ((k >= 48 && k <= 57) || k === 46 || k === 8 || k === 9);
            });



        }
    };
}])

.directive('validarNumeroEntero', ['$timeout', function($timeout) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {
            element.bind('propertychange paste drop blur change input',
            function() {
                $timeout(function() {
                    var texto = $(element).val();

                    var filtrado = texto.replace(/[^0-9]+/g, '');

                    // remover 0's iniciales
                    var posCero = filtrado.indexOf('0');
                    if (posCero === 0 && (Number(filtrado) % 1 !== 0 || Number(filtrado) / 1 === 0)) {
                        filtrado = filtrado.replace(/^0+/, '0');
                    } else {
                        filtrado = filtrado.replace(/^0+/, '');
                    }

                    var resultado = true;
                    if (filtrado.length > 0) {
                        resultado = false;
                    }
                    if (texto.length !== filtrado.length) {
                        $(element).val(filtrado);
                        ngModel.$setViewValue(filtrado);
                    }

                    return resultado;
                }, 100);
            });

            element.bind('keypress', function(e) {
                var k = e.charCode || e.keyCode || 0;
                return ((k >= 48 && k <= 57) || k === 8 || k === 9);
            });
        }
    };
}])
.directive('validarTextoNotificacion', ['$timeout', function($timeout) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {

            element.bind('propertychange paste drop blur change input keyup',
            function() {
                $timeout(function() {
                    var texto = $(element).val();
                    var filtrado = texto.replace(/[^A-Za-z0-9\s]+/g, '');
                    if (texto.length !== filtrado.length) {
                        $(element).val(filtrado);
                        // modificar model
                        ngModel.$setViewValue(filtrado);
                    }
                    var resultado = true;
                    if (filtrado.trim().length > 0) {
                        resultado = false;
                        scope.$broadcast('habilitarEnviarNotificacion', true);
                    } else {
                        scope.$broadcast('deshabilitarEnviarNotificacion', true);
                    }
                    return resultado;
                }, 100);
            });

            element.bind('keypress', function(e) {
                var k = e.charCode || e.keyCode || 0;
                return ((k >= 48 && k <= 57) ||
                        (k >= 65 && k <= 90) || (k >= 97 && k <= 122) ||
                        k === 8 || k === 9 || k === 16 || k === 32);
                        // backspace, tab, shift, space
            });

            scope.$on('habilitarEnviarNotificacion', function() {
                $(element.context.parentElement.parentElement)
                .find('input[type=submit]').prop('disabled', false);
            });

            scope.$on('deshabilitarEnviarNotificacion', function() {
                $(element.context.parentElement.parentElement)
                .find('input[type=submit]').prop('disabled', true);
            });

        }
    };
}])
.directive('validarTextoBuscarArchivos', ['$timeout', function($timeout) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {

            element.bind('propertychange paste drop blur change input keyup',
            function() {
                $timeout(function() {
                    var texto = $(element).val();
                    var filtrado = texto.replace(/[^A-Za-z0-9\x2E\x2C\x2D\x23\s\xE1\xE9\xED\xF3\xFA\xC1\xC9\xCD\xD3\xDA\xF1\xD1]+/g, '');
                    if (texto.length !== filtrado.length) {
                        $(element).val(filtrado);
                        // modificar model
                        ngModel.$setViewValue(filtrado);
                    }

                    var inputNombre = $(element.context.parentElement.parentElement.parentElement)
                    .find('#filtrosArchivoNombre');
                    var inputDescripcion = $(element.context.parentElement.parentElement.parentElement)
                    .find('#filtrosArchivoDescripcion');

                    if (inputNombre.val().trim().length >= 3 || inputDescripcion.val().trim().length >= 3) {
                        scope.$broadcast('habilitarBuscarArchivos', true);
                    } else {
                        scope.$broadcast('deshabilitarBuscarArchivos', true);
                    }

                    return true;
                }, 100);
            });

            element.bind('keypress', function(e) {
                var k = e.charCode || e.keyCode || 0;
                return ((k >= 48 && k <= 57) || //0-9
                        (k >= 65 && k <= 90) || (k >= 97 && k <= 122) || k === 209 || k === 241 ||//a-zA-Z,ñÑ
                        k === 193 || k === 225 || k === 201 || k === 233 || k === 205 || k === 237 ||
                        k === 211 || k === 243 || k === 218 || k === 250 ||
                        k === 46 || k === 44 || k === 45 || k === 35 ||// . , - #
                        k === 8 || k === 9 || k === 16 || k === 32); // backspace, tab, shift, space
            });

            scope.$on('habilitarBuscarArchivos', function() {
                $(element.context.parentElement.parentElement.parentElement)
                .find('input[type=submit]').prop('disabled', false);
            });

            scope.$on('deshabilitarBuscarArchivos', function() {
                $(element.context.parentElement.parentElement.parentElement)
                .find('input[type=submit]').prop('disabled', true);
            });

        }
    };
}])
.directive('sglclick', ['$parse', '$timeout', function($parse, $timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
          var fn = $parse(attr['sglclick']);
          var delay = 300, clicks = 0, timer = null;
          element.on('click', function(event) {
            clicks++;
            if (clicks === 1) {
              timer = $timeout(function() {
                scope.$apply(function() {
                    fn(scope, { $event: event });
                });
                clicks = 0;
              }, delay);
              } else {
                clearTimeout(timer);
                clicks = 0;
              }
          });
        }
    };
}])
.directive('loadingDirective', function() {
    return {
        link: function(scope, element) {
            scope.$on('showLoadingDiv', function() {
                element.css('display', 'block');
            });

            scope.$on('hideLoadingDiv', function() {
                element.css('display', 'none');
            });
        }
    };
});
