$(document).ready(function(){
	console.log($('#nama').val());

	var tbl_data_customer = $('#tbl-customer').DataTable();
	var tbl_data_film = $('#tbl-film').DataTable();
	$('#order').hide();
	getAllCustomer();
	getAllTicket();
	var date = new Date();
	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	var name_month = "";
	switch(month) {
		case 1 :
		name_month = "January";
		break;
		case 2 :
		name_month = "February";
		break;
		case 3 :
		name_month = "March";
		break;
		case 4 :
		name_month = "Apr";
		break;
		case 5 :
		name_month = "May";
		break;
		case 6 :
		name_month = "June";
		break;
		case 7 :
		name_month = "July";
		break;
		case 8 :
		name_month = "August";
		break;
		case 9 :
		name_month = "September";
		break;
		case 10 :
		name_month = "October";
		break;
		case 11 :
		name_month = "November";
		break;
		case 12 :
		name_month = "December";
		break;
	}
	
	$('#lbl-tanggal').html(day + " " + name_month + " " + year);
	$('#btn-search').on('click', function(){
		var customer_id = $('#inp-search').val();
		if(customer_id.length > 0){
			getCustomer(customer_id);
		}else{
			alert('Fill in Customer Id!');
			$('#inp-search').focus();
		}
		
	});

	$('#btn-reset').on('click', function(){
		$('input[type=text]').val("");
		$('input[type=number]').val("");
		$('#order').hide();
	});

	$('#btn-search-film').on('click', function(){
		var ticket_id = $('#inp-search-film').val();
		if(ticket_id.length > 0){
			getTicket(ticket_id);
		}else{
			alert('Fill in Ticket Id!');
			$('#inp-search-film').focus();
		}
		
	});


	$('#btn-order').on('click', function(){
		var customer_id = $('#inp-search').val();
		var ticket_id = $('#inp-search-film').val();
		var buy = $('#inp-buy').val();
		if(customer_id.length == 0 || ticket_id.length == 0 || buy.length == 0){
			alert('Please complete the form first!');
		}else if (buy  <  0 ){
			alert("Jumlah beli tidak boleh minus !");
		}else if(buy  ==  0 ){
			alert("Jumlah beli tidak boleh 0 !");
		}else{
			insert(customer_id,ticket_id,buy);
		}
	});

	function getCustomer(customer_id){
		$.ajax({
			url : "/getCustomer" ,
			type : 'POST',
			contentType: "application/json",
			dataType: 'json',
			data : 
			JSON.stringify({
				"customer_id": customer_id
			}),
			async : false,
			success : function(response, status, error) {
				try{
					console.log(response.length);
					if(response.length > 0){
						$.each(response, function(){
							$('#inp-nama').val(this.name);
							$('#inp-no-telp').val(this.phone);
							$('#inp-email').val(this.email);
						})
					}else{
						alert('Data not found!');
					}
					
				}catch(e){
					alert(e.message);
				}
			},
			error : function(response, status, error) {
				console.log(response);
			}
		});
	}

	function getAllCustomer(){
		$.ajax({
			url : "/getAllCustomer" ,
			type : 'POST',
			contentType: "application/json",
			dataType: 'json',
			data : {},
			async : false,
			success : function(response, status, error) {
				try{
					var count=1;
					console.log(response);
					$.each(response, function(){
						tbl_data_customer.row.add([
							this['customer_id'],
							this['name']
							]).draw(false);
					});
				}catch(e){
					alert(e.message);
				}
			},
			error : function(response, status, error) {
				console.log(response);
			}
		});
	}

	function getAllTicket(){
		$.ajax({
			url : "/getAllTicket" ,
			type : 'POST',
			contentType: "application/json",
			dataType: 'json',
			data : {},
			async : false,
			success : function(response, status, error) {
				try{
					var count=1;
					console.log(response);
					$.each(response, function(){
						tbl_data_film.row.add([
							this['ticket_id'],
							this['film']
							]).draw(false);
					});
				}catch(e){
					alert(e.message);
				}
			},
			error : function(response, status, error) {
				console.log(response);
			}
		});
	}

	function getTicket(ticket_id, time){
		$.ajax({
			url : "/getTicket" ,
			type : 'POST',
			contentType: "application/json",
			dataType: 'json',
			data : 
			JSON.stringify({
				"ticket_id": ticket_id
			}),
			async : false,
			success : function(response, status, error) {
				try{
					console.log(response);
					if(response.length > 0){
						var date = new Date();
						var hours = date.getHours();
						var minutes = date.getMinutes();
						var time = hours+":"+minutes;						
						$.each(response, function(){
							$('#inp-nama-film').val(this.film);
							$('#inp-time').val(this.start_time +" - "+this.finish_time);
						})
					}else{
						alert('Data not found / The movie you entered has passed its broadcast period!');
					}
					
				}catch(e){
					alert(e.message);
				}
			},
			error : function(response, status, error) {
				console.log(response);
			}
		});
	}

	function insert(customer_id,ticket_id,buy){
		$.ajax({
			url : "/insertOrderTicket" ,
			type : 'POST',
			contentType: "application/json",
			dataType: 'json',
			data : 
			JSON.stringify({
				"customer_id" : customer_id,
				"ticket_id": ticket_id,
				"buy" : buy
			}),
			async : false,
			success : function(response, status, error) {
				try{
					console.log(response);
					if(response.message == true){
						alert('Data berhasil di simpan !');
						$('#inp-order').show();
						$('#inp-order').prop('disabled', true);
						$('#inp-order').val(response.order_max);
						$('#order').show();
					}else{
						alert(response.message);
					}
					
				}catch(e){
					alert(e.message);
				}
			},
			error : function(response, status, error) {
				console.log(response);
			}
		});
	}
});