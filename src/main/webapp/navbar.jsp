<%@page import="br.com.lp3superior.modelo.User"%>
<header>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top" style="background-color:rgba(0, 0, 0, 0.6);">
		<div class="container-fluid">

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
				aria-controls="navbarCollapse" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarCollapse">
				<ul class="navbar-nav me-auto mb-2 mb-md-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="#">Home</a></li>
				</ul>
				<% User user = (User) session.getAttribute("user"); %>
				<% if (user != null){ %>				
					<ul class="navbar-nav flex-row ml-md-auto d-none d-md-flex">
						<li class="nav-item"><a class="nav-link"
							href="ClientController?action=toManage">Client List</a>
						</li>
						<li class="nav-item"><a class="nav-link active"
							href="#"><%=user.getFirstName()%></a>
						</li>
						<li class="nav-item"><a class="nav-link active"
							href="UserController?action=logout">Logout</a>
						</li>
	
					</ul>
				<% } %>
			</div>
		</div>
	</nav>
</header>