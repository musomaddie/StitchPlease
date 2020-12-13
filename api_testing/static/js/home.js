let ns = {};

// Create the model instance
ns.model = (function() {
	"use strict";

	let $event_pump = $('body');

	// Return the API
	return {
		'read': function() {
			let ajax_options = {
				type: 'GET',
				url: 'api/people',
				accepts: 'application/json',
				dataType: 'json'
			};
			$.ajax(ajax_options)
			.done(function(data) {
				$event_pump.trigger("model_read_success", [data]);
			})
			.fail(function(xhr, textStatus, errorThrown) {
				$event_pump.trigger("model_error", [xhr, textStatus, errorThrown]);
			})
		},
		create: function(fname, lname) {
			let ajax_options = {
				type: 'POST',
				url: 'api/people',
				accepts: 'application/json',
				contentType: 'application/json',
				dataType: 'json',
				data: JSON.stringify({
					'fname': fname,
					'lname': lname
				})
			};
			$.ajax(ajax_options)
			.done(function(data) {
				$event_pump.trigger('model_create_success', [data]);
			})
			.fail(function(xhr, textStatus, errorThrown) {
				$event_pump.trigger('model_error', [xhr, textStatus, errorThrown]);
			})
		}
	};
}());


// Create the view instance
ns.view = (function() {
	'use strict';

	let $fname = $('#fname'),
		$lname = $('#lname');

	// return the API
	return {
		build_table: function(people) {
			let rows = ''

			$('.people talbe > tbody').empty();

			if (people) {
				for (let i=0, l=people.length; i < l; i++) {
					rows += `<tr><td class="fname">${people[i].fname}</td><td class="lname">${people[i].lname}</td><td>${people[i].timestamp}</td></tr>`;
				}
				$('table > tbody').append(rows);
			}
		},
		error: function(error_msg) {
			$('.error')
				.text(error_msg)
				.css('visibility', 'visible');
			setTimeout(function() {
				$('.error').css('visibility', 'hidden');
			}, 3000)
		}
	};
}());


// Create the controller
ns.controller = (function(m, v) {
	'use strict';

	let model=m,
		view=v,
		$event_pump = $('body'),
		$fname = $('#fname'),
		$lname = $('#lname');

	setTimeout(function() {
		model.read();
	}, 100)


