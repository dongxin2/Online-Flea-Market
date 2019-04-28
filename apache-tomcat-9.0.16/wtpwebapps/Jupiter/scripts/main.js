(function() {
  
/* step2: variables */
	var user_id = sessionStorage.getItem('user_id'); //'1111';
	var user_fullname = sessionStorage.getItem('name'); //'John Smith';
//	var lng = -122.08;
//	var lat = 37.38;

	/* step3: main function(entrance) */
	init();

	/* step4: define init function */
	function init() {
		var loginMess = document.getElementById('login');
		if(user_fullname) {
			loginMess.innerHTML = 'Sign out';
		}
		// Register event listeners
		$('nearby-btn').addEventListener('click', loadNearbyItems);
		$('fav-btn').addEventListener('click', loadFavoriteItems);
		$('recommend-btn').addEventListener('click', loadRecommendedItems);
//		$('fav-link').addEventListener('click', addFavoriteItems);
		
		
		var welcomeMsg = $('welcome-msg');
        		welcomeMsg.innerHTML = 'Welcome, ' + user_fullname;
        
        		// step 7
//        		initGeoLocation();
	}
	
	/* step5: create $ function */
	/**
	 * A helper function that creates a DOM element <tag options...>
	 */
	function $(tag, options) {
		if (!options) {
			return document.getElementById(tag);
		}
		var element = document.createElement(tag);

		for ( var option in options) {
			if (options.hasOwnProperty(option)) {
				element[option] = options[option];
			}
		}
		return element;
	}
	
		/* step6: create AJAX helper function */
	/**
	 * @param method - GET|POST|PUT|DELETE
	 * @param url - API end point
	 * @param callback - This the successful callback
	 * @param errorHandler - This is the failed callback
	 */
	function ajax(method, url, data, callback, errorHandler) {
		var xhr = new XMLHttpRequest();

		xhr.open(method, url, true);

		xhr.onload = function() {
			if (xhr.status === 200) {
				callback(xhr.responseText);
			} else if (xhr.status === 403) {
				onSessionInvalid();
			} else {
				errorHandler();
			}
		};

		xhr.onerror = function() {
			console.error("The request couldn't be completed.");
			errorHandler();
		};

		if (data === null) {
			xhr.send();
		} else {
			xhr.setRequestHeader("Content-Type",
					"application/json;charset=utf-8");
			xhr.send(data);
		}
	}
	
	/** step 7: initGeoLocation function **/
	function initGeoLocation() {
		if (navigator.geolocation) {
			// step 8
			navigator.geolocation.getCurrentPosition(onPositionUpdated,
					onLoadPositionFailed, {
						maximumAge : 60000
					});
			showLoadingMessage('Retrieving your location...');
		} else {
			// step 9
			onLoadPositionFailed();
		}
	}

	/** step 8: onPositionUpdated function **/
	function onPositionUpdated(position) {
		lat = position.coords.latitude;
		lng = position.coords.longitude;

		// step 11
		loadNearbyItems();
	}

	/** step 9: onPositionUpdated function **/
	function onLoadPositionFailed() {
		console.warn('navigator.geolocation is not available');
		
		//step 10
		getLocationFromIP();
	}
	
		/** step 10: getLocationFromIP function **/
	function getLocationFromIP() {
		// Get location from http://ipinfo.io/json
		var url = 'http://ipinfo.io/json'
		var req = null;
		ajax('GET', url, req, function(res) {
			var result = JSON.parse(res);
			if ('loc' in result) {
				var loc = result.loc.split(',');
				lat = loc[0];
				lng = loc[1];
			} else {
				console.warn('Getting location by IP failed.');
			}
			// step 11
			loadNearbyItems();
		});
	}

	/** step 11: loadNearbyItems function **/
	/**
	 * API #1 Load the nearby items API end point: [GET]
	 * /Dashi/search?user_id=1111&lat=37.38&lon=-122.08
	 */
	function loadNearbyItems() {
		console.log('loadNearbyItems');
		// step 12
		activeBtn('nearby-btn');

		// The request parameters
		var url = './search';
		var params = 'user_id=' + user_id + '&term=all';
		var req = JSON.stringify({});

		// step 13
		// display loading message
		showLoadingMessage('Loading nearby items...');

		// make AJAX call
		ajax('GET', url + '?' + params, req,
		// successful callback
		function(res) {
			var items = JSON.parse(res);
			if (!items || items.length === 0) {
				// step 14
				showWarningMessage('No nearby item.');
			} else {
				// step 16
				listItems(items);
			}
		},
		// failed callback
		function() {
			// step 15
			showErrorMessage('Cannot load nearby items.');
		});
	}
	
	/** step 18: loadFavoriteItems function **/
	function loadFavoriteItems() {
		console.log('loadFavoriteItems');
		// step 12
		activeBtn('fav-btn');

		// The request parameters
		var url = './history';
		var params = 'user_id=' + user_id ;
		var req = JSON.stringify({});

		// step 13
		// display loading message
		showLoadingMessage('Loading favorite items...');

		// make AJAX call
		ajax('GET', url + '?' + params, req,
		// successful callback
		function(res) {
			var items = JSON.parse(res);
			if (!items || items.length === 0) {
				// step 14
				showWarningMessage('No favorite item.');
			} else {
				// step 16
				listItems(items);
			}
		},
		// failed callback
		function() {
			// step 15
			showErrorMessage('Cannot load nearby items.');
		});
	}
	
	/** step 20: loadRecommendedItems function **/
	function loadRecommendedItems() {
		console.log('loadRecommendedItems');
		// step 12
		activeBtn('recommend-btn');

		// The request parameters
		var url = './recommendation';
		var params = 'user_id=' + user_id ;
		var req = JSON.stringify({});

		// step 13
		// display loading message
		showLoadingMessage('Loading recommend items...');

		// make AJAX call
		ajax('GET', url + '?' + params, req,
		// successful callback
		function(res) {
			var items = JSON.parse(res);
			if (!items || items.length === 0) {
				// step 14
				showWarningMessage('No favorite item.');
			} else {
				// step 16
				listItems(items);
			}
		},
		// failed callback
		function() {
			// step 15
			showErrorMessage('Cannot load nearby items.');
		});
	}
	
	/** step 19: addFavoriteItems function **/
//	function addFavoriteItems() {
//		console.log('addFavoriteItems');
//		//TODO
//		// The request parameters
//		var url = './history';
//		var params = 'user_id=' + user_id ;
//		var req = JSON.stringify({});
//
//		// step 13
//		// display loading message
//		showLoadingMessage('Loading favorite items...');
//
//		// make AJAX call
//		ajax('GET', url + '?' + params, req,
//		// successful callback
//		function(res) {
//			var items = JSON.parse(res);
//			if (!items || items.length === 0) {
//				// step 14
//				showWarningMessage('No nearby item.');
//			} else {
//				// step 16
//				listItems(items);
//			}
//		},
//		// failed callback
//		function() {
//			// step 15
//			showErrorMessage('Cannot load nearby items.');
//		});
//	}

		/** step 12: activeBtn function **/
	
	/**
	 * A helper function that makes a navigation button active
	 * 
	 * @param btnId - The id of the navigation button
	 */
	function activeBtn(btnId) {
		var btns = document.getElementsByClassName('main-nav-btn');

		// deactivate all navigation buttons
		for (var i = 0; i < btns.length; i++) {
			btns[i].className = btns[i].className.replace(/\bactive\b/, '');
		}

		// active the one that has id = btnId
		var btn = $(btnId);
		btn.className += ' active';
	}

		/** step 13: showLoadingMessage function **/
	function showLoadingMessage(msg) {
		var itemList = $('item-list');
		itemList.innerHTML = '<p class="notice"><i class="fa fa-spinner fa-spin"></i> '
				+ msg + '</p>';
	}
	
	/** step 14: showWarningMessage function **/
	function showWarningMessage(msg) {
		var itemList = $('item-list');
		itemList.innerHTML = '<p class="notice"><i class="fa fa-exclamation-triangle"></i> '
				+ msg + '</p>';
	}
	
	/** step15: showErrorMessage function **/
	function showErrorMessage(msg) {
		var itemList = $('item-list');
		itemList.innerHTML = '<p class="notice"><i class="fa fa-exclamation-circle"></i> '
				+ msg + '</p>';
	}

	/** step16: listItems function **/
	/**
	 * @param items - An array of item JSON objects
	 */
	function listItems(items) {
		// Clear the current results
		var itemList = $('item-list');
		itemList.innerHTML = '';

		for (var i = 0; i < items.length; i++) {
			// step 17
			addItem(itemList, items[i]);
		}
	}

		/** step17: addItem function **/
	/**
	 * Add item to the list
	 * @param itemList - The <ul id="item-list"> tag
	 * @param item - The item data (JSON object)
	 */
	function addItem(itemList, item) {
		var item_id = item.item_id;

		// create the <li> tag and specify the id and class attributes
		var li = $('li', {
			id : 'item-' + item_id,
			className : 'item'
		});

		// set the data attribute
		li.dataset.item_id = item_id;
		li.dataset.favorite = item.favorite;

		// item image
		if (item.image_url) {
			li.appendChild($('img', {
				src : item.image_url
			}));
		} else {
			li.appendChild($('img', {
				src : 'https://assets-cdn.github.com/images/modules/logos_page/GitHub-Mark.png'
			}))
		}
		// section
		var section = $('div', {});

		// title
		var title = $('a', {
			href : item.url,
			target : '_blank',
			className : 'item-name'
		});
		title.innerHTML = item.name;
		section.appendChild(title);

		// category
		var category = $('p', {
			className : 'item-category'
		});
		category.innerHTML = 'Category: ' + item.category;
		section.appendChild(category);
		
		// price
		var category = $('p', {
			className : 'item-price'
		});
		category.innerHTML = 'Price: ' + item.price;
		section.appendChild(category);
		
		// seller_id
		console.log(item);
		var category = $('p', {
			className : 'seller-id'
		});
		category.innerHTML = 'Seller: ' + item.seller_id;
		section.appendChild(category);

		var stars = $('div', {
			className : 'stars'
		});
		
		for (var i = 0; i < item.rating; i++) {
			var star = $('i', {
				className : 'fa fa-star'
			});
			stars.appendChild(star);
		}

		if (('' + item.rating).match(/\.5$/)) {
			stars.appendChild($('i', {
				className : 'fa fa-star-half-o'
			}));
		}

		section.appendChild(stars);

		li.appendChild(section);

		// address
		var address = $('p', {
			className : 'item-address'
		});

		address.innerHTML = item.description.replace(/,/g, '<br/>').replace(/\"/g,
				'');
		li.appendChild(address);

		// favorite link
		var favLink = $('p', {
			className : 'fav-link'
		});

		favLink.onclick = function() {
			changeFavoriteItem(item_id, item.favorite);
			changeIcon('fav-icon-' + item_id);
		};

		favLink.appendChild($('i', {
			id : 'fav-icon-' + item_id,
			className : item.favorite ? 'fa fa-heart' : 'fa fa-heart-o'
		}));

		li.appendChild(favLink);

		itemList.appendChild(li);
	}
	
	function changeFavoriteItem(item_id, favorite){
		console.log('changeFavoriteItems');
		console.log(favorite);
		// The request parameters
		var url = './history';
		var params = 'user_id=' + user_id + '&favorite=' + item_id;
		var req = JSON.stringify({});

		// step 13
		// display loading message
//		showLoadingMessage('Changing favorite items...');

		// make AJAX call
		if(!favorite){
			ajax('POST', url + '?' + params, req,
			// successful callback
			function(res) {
				var result = JSON.parse(res);
				if (result.result == "SUCCESS") {
					// step 14
					console.log('Add Item succesfully');
				} else {
					// step 16
					console.log('Add Item unsuccesfully');
				}
			},
			// failed callback
			function() {
				// step 15
				showErrorMessage('Cannot add favorite items.');
			});
		} else {
			ajax('DELETE', url + '?' + params, req,
			// successful callback
			function(res) {
				var result = JSON.parse(res);
				if (result.result == "SUCCESS") {
					// step 14
					console.log('delete Item succesfully');
				} else {
					// step 16
					console.log('delete Item unsuccesfully');
				}
			},
			// failed callback
			function() {
				// step 15
				showErrorMessage('Cannot delete favorite items.');
			});
		}
	}
	
	function changeIcon(iconID){
		console.log(iconID);
		console.log("this");
		if($(iconID).className=="fa fa-heart"){
			$(iconID).className = "fa fa-heart-o";
		}else{
			$(iconID).className = "fa fa-heart";
		}
	}


})()
