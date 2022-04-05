<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Management</title>
<jsp:include page="../import_bootstrap.jspf" />
<jsp:include page="../import_jquery.jspf" />
<script>
$(document).ready(function() {
	$('#submit').click(function(event){
		var nameClient = $('#nameClient').val();
		var cpfClient = $('#cpfClient').val();
		var id = $('#idClient').val();
        $.ajax({
            type:'POST',
            data: {
            	idClient : id,
            	name : nameClient,
    			cpf : cpfClient,
    			action : 'handle_client'
            },
            dataType: 'json',
            url:'ClientController',
            success: function(client){
            	$("#idClient").val("");
                $("#nameClient").val("");
                $("#cpfClient").val("");
                updatePagination();
				updateTableWithPagination('presentPage');
            },
            error: function(jqXHR, textStatus, message) {
            	alert(jqXHR.responseText);
            }
        });
        
    });
	
	$('#tableResposta').on('click','.del',function(event){
      	var $linhaCorrente = $(this).closest("tr");
        var id = $linhaCorrente.find(".idCell").html();        
        var confirmacao = confirm("D'you wanna delete it: " + id + " ?");
        if (confirmacao == true) {
        	$.ajax({
        	    type:'GET',
        	    data: {
        	    	idClient : id,
    				action : 'delete_client'
        	    },
        	    dataType: 'text',
        	    url:'ClientController',
        	    success: function(response){
					updatePagination();
					updateTableWithPagination('presentPage');
        	    },
        	    error: function(jqXHR, textStatus, message) {
        	    	alert(jqXHR.responseText);
        	    }
        	});
        }
    });	
	
	$('#tableResposta').on('click','.alt',function(event){
		
		$('#tableResposta').find('.editMode').each(function(){
			  $(this).removeClass("editMode");
		});
		
      	var $linhaCorrente = $(this).closest("tr");
        var id = $linhaCorrente.find(".idCell").html();  
        var name = $linhaCorrente.find(".nameClientCell").html();
        var cpf = $linhaCorrente.find(".cpfCell").html();
        $("#idClient").val(id);
        $("#nameClient").val(name);
        $("#cpfClient").val(cpf);
        
        $linhaCorrente.addClass('editMode');
    });
	
	$('ul').on('click','.num',function(event){
		currentPage = $(this).html();
		updateTableWithPagination('presentPage');		
	});
	
	$("#nextNavPagination").click(function(){	
		updateTableWithPagination('next');	
	});
	
	$("#previousNavPagination").click(function(){	
		updateTableWithPagination('previous');	
	});
	
	var currentPage = 1;
	var maxPagesQuantity = ${pagesQuantity};
	
	function updatePagination() {
		$.ajax({
    	    type:'GET',
    	    data: {
    	    	movementDirection : 'presentPage',
    	    	currentPage: currentPage,
				action : 'get_page_quantity'
    	    },
    	    dataType: 'json',
    	    url:'ClientController',
    	    success: function(pageQuantity){
    	    	if (maxPagesQuantity < pageQuantity) {
    	    		maxPagesQuantity = pageQuantity;
    	    		var $li =  $("<li/>").addClass('page-item')
    	    			.append( $("<a>").text(maxPagesQuantity).addClass('page-link num').attr("href","#"));   	    					
    	    		$('.pagination li:nth-child(' + maxPagesQuantity + ')').after($li);
    	    	} else if (maxPagesQuantity > pageQuantity) { 
    	    		var page = maxPagesQuantity + 1
    	    		$('.pagination li:nth-child('+ page +')').remove();
    	    		maxPagesQuantity = pageQuantity   	    		
    	    	}
    	    },
    	    error: function(jqXHR, textStatus, message) {
    	    	alert(jqXHR.responseText);
    	    }
    	});
	}
	
	function updateTableWithPagination(newMovementDirection) {
		$.ajax({
    	    type:'GET',
    	    data: {
    	    	movementDirection : newMovementDirection,
    	    	currentPage: currentPage,
    	    	action : 'do_client_pagination'
    	    },
    	    dataType: 'json',
    	    url:'ClientController',
    	    success: function(client){
    	    	if (client.length == 0){
    	    		currentPage--;
    	    		updateTableWithPagination('presentPage');
    	    	}
    	    	if (newMovementDirection == 'next') currentPage++;
    	    	else if (newMovementDirection == 'previous') currentPage--;
    	    	updateNextAndPreviousButton();    		    
    	    	makeTable(client);
    	    	activateCurrentPageOnPagination(currentPage);
    	    },
    	    error: function(jqXHR, textStatus, message) {
    	    	alert(jqXHR.responseText);
    	    }
    	});	
	}
	
	function updateNextAndPreviousButton() {
		$("#tableResposta").find("tr:gt(0)").remove();
	    if (currentPage == maxPagesQuantity){
	    	$("#nextNavPagination").closest("li").addClass('disabled');
	    	$('#previousNavPagination').closest("li").removeClass('disabled');
	    }else if (currentPage == 1){
	    	$("#previousNavPagination").closest("li").addClass('disabled');	
	    	$('#nextNavPagination').closest("li").removeClass('disabled');
	    }else{
	    	$('#previousNavPagination').closest("li").removeClass('disabled');
	    	$('#nextNavPagination').closest("li").removeClass('disabled');
	    }
	}
	
	function makeTable(client) {
		var $table = $('#tableResposta');    	
		$.each(client, function( key, client ) {
			var $deleteButton =  $("<a/>").addClass('del btn btn-danger btn-sm').attr("href","#").text('Delete');
	    	var $updateButton =  $("<a/>").addClass('alt btn btn-warning btn-sm').attr("href","#").text('Update');
	    	
			var newRow = $("<tr>").append($("<td>").text(client.idClient).addClass('idCell')) 
				.append($("<td>").text(client.name).addClass('nameClientCell'))     
				.append($("<td>").text(client.cpf).addClass('cpfCell'))
				.append($("<td>").append($deleteButton))
				.append($("<td>").append($updateButton));
			newRow.appendTo($table);
		});
	}
	
	function activateCurrentPageOnPagination(pageNumber){
		$('.pagination').find('.active').each(function(){
			  $(this).removeClass("active");
		});
		$('.pagination').find('.num').each(function(){
			if (pageNumber == $(this).html()){
				$(this).closest("li").addClass('active');
			}
		});
	}
	
});


</script>
</head>

<body>

<jsp:include page="../navbar.jsp" />
<div class="mt-5"></div>

	<h1 class="text-center fw-bold display-5">Client Management</h1>
	<div class="container">
		<div class="row d-flex justify-content-center">
			<div class="col-auto bg-light text-black border rounded-3">
				<form id="formclient">
					<input type="hidden" id="idClient" />
					<div id="textResposta"></div>
					<div class="mb-3">
					    <label for="nameClient" class="fw-bold">Client Name</label>
					    <input type="text" class="form-control" id="nameClient">
				  	</div>
				  	<div class="mb-3">
					    <label for="cpfClient" class="fw-bold">Client CPF</label>
					    <input type="text" class="form-control" id="cpfClient">
				  	</div>
				  	<input type="button" id="submit" value="Submit" class="btn btn-primary"/>
				</form>
			</div>
		</div>
	</div>
	
	<div class="container" id='divtable'>
		<div class="row mt-2 d-flex justify-content-center">
			<div class="col-auto">
				<table border="1px" id="tableResposta" class="table justify-content-center">
					<tr class="table-light">
						<th>Id</th>
						<th>Name</th>
						<th>Cpf</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach var="client" items="${clients}">
						<tr>
							<td class='idCell'><c:out value="${client.idClient}"></c:out></td>
							<td class='nameClientCell'><c:out value="${client.name}"></c:out></td>
							<td class='cpfCell'><c:out value="${client.cpf}"></c:out></td>
							<td><a class='btn btn-danger btn-sm del' href='#'>Delete</a></td>
							<td><a class='btn btn-warning btn-sm alt' href='#'>Update</a></td>
						</tr>
					</c:forEach>
				</table>
				<nav>
				  	<ul class="pagination justify-content-center">
					    <li class="page-item disabled">
					      	<a id="previousNavPagination" class="page-link" href="#" tabindex="-1">Previous</a>
					    </li>
						<c:forEach var = "i" begin = "1" end = "${pagesQuantity}">			
							<c:choose> 
					         	<c:when test = "${i == 1}">
						            <li class="page-item active">
										<a class="page-link num" href="#"><c:out value="${i}"/></a>
									</li>
					         	</c:when>         
					         	<c:otherwise>
						            <li class="page-item">
										<a class="page-link num" href="#"><c:out value="${i}"/></a>
									</li>
					         	</c:otherwise>
					       	</c:choose>							
			           	</c:forEach>
						<li class="page-item">
					      	<a id="nextNavPagination" class="page-link" href="#">Next</a>
					    </li>
				  	</ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>