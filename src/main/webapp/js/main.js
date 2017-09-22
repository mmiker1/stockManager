$(document).ready(function() {
	
	$(document).on('keypress', 'input', function(e) {

		  if(e.keyCode == 13 && e.target.type !== 'submit') {
		    e.preventDefault();
		    searchSymbol();
		  }

	});
	
	
	setInterval(function() {
		refreshPortfolioList();
	}, 5000);
});

function searchSymbol(){
	var symb = $("#inputSymbol").val().toUpperCase().trim();
	$("#inputSymbol").val(symb);
	
	searchSymbolMessage("");
	$("#searchStockItem").html("");
	if(symb == ""){
		searchSymbolMessage("Error. Symbol cannot be empty.");
		return;
	}
	
	var data = {"symbol" : symb};
	$.ajax ({
		url : 'searchSymbol.ajax',
		data : data,
		dataType: "json",
		type : "POST",

		success : function(response) {
			if(response.symbol == "" ){
				searchSymbolMessage("Error. Symbol not found.");
			} else {
				showStockItem(response);
			}
			
		},
		error : function(xhr, status, error) {
			searchSymbolMessage("Error. Technical problem.");
		}
	});
	
}

function searchSymbolMessage(mess){
	$("#searchMessage").html(mess);
}

function showStockItem(response){

	var changeClass = "color-red";
	if(response.up) changeClass = "color-green";
	var html = "<table>" +
	           "<tr><td>Symbol: </td><td><b>" + response.symbol + "</b></td></tr>" +
	           "<tr><td>Name: </td><td><b>" + response.name + "</b></td></tr>" +
	           "<tr><td>Price: </td><td><b>" + response.price + "</b></td></tr>" +
	           "<tr class='" + changeClass + "'><td>Last Change: </td><td><b>" + response.change + "</b></td></tr>" +
	           "<tr><td>Currency: </td><td><b>" + response.currency + "</b></td></tr>";
	if(parseFloat(response.price) > 0 ) {
		html += "<tr><td>Count: </td><td><input class='form-control' id='portItemsCount' value=1></td></tr>";
	}
	if(response.itemsCount < 10 && parseFloat(response.price) > 0){
		html += "<tr><td colspan=2 align='right'><br><button title='Add to Portfolio' onClick=\"addToPortfolio('" + response.symbol + "')\" class='btn btn-primary btn-xs'>portfolio " +
               "<span class='glyphicon glyphicon-plus' aria-hidden='true'></span>" +
               "</button></td></tr>";
	}
	    html += "</table>";
	
	$("#searchStockItem").html(html);
}

function removeFromPortfolio(id){
	
	var r = confirm("Are you sure to remove this item?");
	if (r != true) {
		return;
	}
	var data = {"command": "remove", "id" : id};
	$.ajax ({
		url : 'portfolioFunctions.ajax',
		data : data,
		dataType: "json",
		type : "POST",

		success : function(response) {
			if(response.status == "OK"){
				errorMessage("Item was successfully removed from portfolio");
			}
			
		},
		error : function(xhr, status, error) {
			
		}
	});
}

function addToPortfolio(symb){
	var count = $("#portItemsCount").val().trim();
	var data = {"command": "add", "symbol" : symb, "count": count};
	
	$.ajax ({
		url : 'portfolioFunctions.ajax',
		data : data,
		dataType: "json",
		type : "POST",

		success : function(response) {
			if(response.status == "OK"){
				errorMessage("Item was successfully added to portfolio");
				$("#searchStockItem").html("");
				refreshPortfolioList();
			} else {
				errorMessage(response.error);
			}
			
		},
		error : function(xhr, status, error) {
			
		}
	});
	
	
}

function errorMessage(mess){
	$("#errorMessage").addClass("opacity100");
	$("#errorMessage").show();
	$("#errorMessage").html(mess).fadeOut(5000);
}


function refreshPortfolioList(){
	var data = {};
	$.ajax ({
		url : 'getPortfolioItems.ajax',
		data : data,
		dataType: "json",
		type : "POST",

		success : function(response) {
			if(response.itemsCount == 0){
				var html = "<div class='fontsize24' style='padding-top:70px;text-align:center;'>Please add items to Portfolio</div>";
				$("#stockPortfolio").html(html);
			} else {
				var html = "<table width='100%'>" +
				           "<tr><td></td>" +
				           "<td class='padding3'>Symb</td>" + 
				           "<td class='padding3'>Name</td>" + 
				           "<td class='padding3'>Curr</td>" + 
				           "<td class='padding3'>Price</td>" +
				           "<td class='padding3'>Count</td>" +
				           "<td class='padding3' align='right'>Value</td>" +
				           "<td class='padding3' align='right'>Change</td>" +
				           "<td></td></tr>";
				
				var total = 0;   
				
				for(var i=0; i < response.items.length; i++){
					total += parseFloat(response.items[i].marketValue);
					var line = i+1;
					var changeClass = "color-red";
					if(response.items[i].up) changeClass = "color-green";
		            html += "<tr class='padding3 " + changeClass +"'><td class='padding3'>" + line + ". </td><td class='padding3'>" + response.items[i].symbol + "</td>" +
		                     "<td class='padding3'>" + response.items[i].name + "</td>" +
		                     "<td class='padding3'>" + response.items[i].currency + "</td>" +
		                       "<td class='padding3'>" + response.items[i].price + "</td>" +
		                       "<td class='padding3'>" + response.items[i].count + "</td>" +
		                       "<td class='padding3' align='right'>" + response.items[i].marketValue + "</td>" +
		                       "<td class='padding3' align='right'>" + response.items[i].change + "</td>" +
		                       "<td class='padding3'><button title='remove' onClick=\"removeFromPortfolio('" + response.items[i].id + "')\" class='btn btn-error btn-xs'>" +
                                  "<span  class='glyphicon glyphicon-remove' aria-hidden='true'></span>" +
                                  "</button></td>" +
		                     "</tr>";
				}
				
				html += "<tr class='padding3'>" +
				            "<td class='padding3' align='right' colspan=3>Total Value:</td>" +
				            "<td class='padding3' colspan=3>USD</td>" +
				            "<td class='padding3' align='right'>" + total.toFixed(2) + "</td>" +
				            "<td colspan=2>&nbsp;</td>" +
				        "</tr>";
				
		        html += "</table>";
		        $("#stockPortfolio").html(html);
			}
			
		},
		error : function(xhr, status, error) {
			
		}
	});
}

