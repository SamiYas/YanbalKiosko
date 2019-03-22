angular.module('YanbalKiosko.services')

.factory('ConfiguracionService', ['$q', 'BdHelper', '$cordovaSQLite', 'queryConstants', '$filter', 'libreriaCorporativa', function ($q, BdHelper, $cordovaSQLite, queryConstants, $filter, libreriaCorporativa) {
    var self = this;

    self.GetSincDate = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        BdHelper.query(queryConstants.selectVariable, ['FECHA_SINCRONIZACION']).then(function (selectNotificacion) {
            if (BdHelper.getAll(selectNotificacion)[0]) {
                deferred.resolve(BdHelper.getAll(selectNotificacion)[0].VALOR);
            } else {
                deferred.resolve('');
            }
        }, function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject($filter('translate')('ERRORGENERICO'));
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    self.UpdateMobileData = function (mobileDataSelected) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        var mobileData = mobileDataSelected ? '1' : '0';
        BdHelper.query(queryConstants.insertVariable, ['DATOS_MOVILES', mobileData]).then(function (insertData) {
            deferred.resolve(insertData);
        }, function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject($filter('translate')('ERRORGENERICO'));
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    self.GetMobileData = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        BdHelper.query(queryConstants.selectVariable, ['DATOS_MOVILES']).then(function (selectMobileData) {
            if (BdHelper.getById(selectMobileData)) {
                deferred.resolve(BdHelper.getById(selectMobileData).VALOR);
            } else {
                deferred.resolve('0');
            }
        }, function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject($filter('translate')('ERRORGENERICO'));
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    return self;
}]);