'use strict'

angular.module('app.controllers', [])

.controller('RandomQuoteCtrl', function($scope, QuoteService) {
	QuoteService.random()
		.$promise.then(function(quote) {
			$scope.quote = quote;
		});
})
.controller('SaveQuoteCtrl', function($scope, $state, QuoteService) {
    $scope.saveQuote = function() {
        QuoteService.save(
            $scope.quote,
            function(response) {
                $state.go("quote", {});
            },
            function(err) {
                console.log(err);
            }
        );
    };
})
.controller('AuthorQuoteCtrl', function($scope, AuthorService, $stateParams) {
	    AuthorService.get({authorId: $stateParams.authorId}, function(author) {	
	    	$scope.author = author;
    	});
});
