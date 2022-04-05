<script>
$(document).ready(function() {
	$('#submit_login').click( function(event){
		var email_form = $('#email_form').val();
		var password_form = $('#password_form').val();
		$.ajax({
            type:'POST',
            data: {
            	email : email_form,
            	password : password_form,
    			action : 'login'
            },
            dataType: 'json',
            url:'UserController',
            success: function(responseText){
            	$("#email_form").val(""); 
                $("#password_form").val("");
                if (responseText == 'OK'){
                	window.location.href = 'ClientController?action=toManage';
                }else{
                	alert('login error');
                }
            },
            error: function(jqXHR, textStatus, message) {
            	alert('server error');
            }
        });
        
    });
});	
</script>

<!-- SIGN IN -->
<div class="row d-flex justify-content-center">

    <div class="col-auto bg-light border">

        <h1 class="text-center bg-light text-black px-0 py-3 mx-0 my-0 text-uppercase fw-bold">sign in</h1>

        <!-- Form -->
        <form>
            <!-- Field -->
            <div class="row text-uppercase fw-bold">

                <div class="col">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" name="email" id="email_form" maxlength="40" placeholder="example@name.com" required="required">

                        <label for="password">Password</label>
                        <input type="password" class="form-control" name="password" id="password_form" placeholder="Ex4mp1&" required="required">
                    </div>
                </div>
                
            </div>
            <!-- Button -->
            <div class="row py-1">
                <div class="col text-center">
                    <input type="button" id="submit_login" class="btn btn-danger btn-lg"  value="ENTER">
                </div>
            </div>
            <div class="row py-1">
                <div class="col text-center">
                    <p class="py-0 my-0">Haven't an account?</p>
                    <a class="btn btn-secondary" href="UserController?action=new_account">SIGN UP</a>
                </div>
            </div>

        </form>

    </div>

</div>