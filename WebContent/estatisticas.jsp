<%@ page language="java" contentType="text/html; charset=UTF-8"%>
	
	<%@page pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=0.9">

<!------ Include the above in your HEAD tag ---------->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>                    
<script src="resource/javascript/util/fa.js"></script>

<style type="text/css">

html, body{
	height: 100%;
}

body{
background-image: url('resource/img/account.jpg');
background-repeat: no-repeat;
}


.content {
 min-height: 100%; 
 padding-bottom: 100px; 
}

footer{

}


nav{
box-shadow: 0.5rem 0.5rem 1rem 0 rgba(0, 0, 0, 0.1);
}


.dropdown-item.active, .dropdown-item:active{
    color: #fff;
    background-color: #7500B3;
}



</style>

<title>My Account</title>
</head>
<body>

<div class="content">
                   <header>
                         <nav class="navbar navbar-expand navbar-dark">
                            <a class="navbar-brand" href="home.jsp">
                           <i class="fas fa-arrow-left fa-md"></i>
                          </a>
                            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample02" aria-controls="navbarsExample02" aria-expanded="false" aria-label="Toggle navigation">
      							 <span class="navbar-toggler-icon"></span>
     						 </button>
                            <div class="collapse navbar-collapse" id="navbarsExample02">
                                <ul class="navbar-nav mr-auto">
                                    <li class="nav-item">
                                        <a class="nav-link" href="home.jsp"> <i class="fas fa-home fa-sm"></i> Home</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="categorias"> <i class="fas fa-clipboard fa-sm"></i> Listas</a>
                                    </li>
                                    <li class="nav-item">
                                    	<a class="nav-link" href="produtos" ><i class="fas fa-shopping-cart fa-sm"></i> Produtos</a>
                                    </li>
                                </ul>
                            </div>
                            
                            
                            <c:if test="${!user.miniatura.isEmpty() && user.miniatura != null}">
                            		<div class="icon-perfil" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            			<input  class="img-perfil" type="image" src="${user.miniatura }" />
                            		</div>
                            </c:if>
                            
                            
                             <c:if test="${user.miniatura.isEmpty() || user.miniatura == null}">
                            <button type="button" class="btn btn-outline-light" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <!-- botao user -->
									<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-circle" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
										  <path d="M13.468 12.37C12.758 11.226 11.195 10 8 10s-4.757 1.225-5.468 2.37A6.987 6.987 0 0 0 8 15a6.987 6.987 0 0 0 5.468-2.63z"/>
										  <path fill-rule="evenodd" d="M8 9a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
										  <path fill-rule="evenodd" d="M8 1a7 7 0 1 0 0 14A7 7 0 0 0 8 1zM0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8z"/>
									</svg>
							</button>
							</c:if>
							
                            <div class="dropdown-menu dropdown-menu-right">
                                <a class="dropdown-item" href="#">Minha Conta</a>
                                <a class="dropdown-item" href="#">Another action</a>
                                <a class="dropdown-item" href="#">Something else here</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="logout">Logout</a>
                            </div>

</nav>
</header> 	<!--  END NAVBAR  -->
				
				
				
				


	<div class="container">

		<div class="card card-signin my-5" style="border-radius: 1em">
			<article class="card-body mx-auto">
				<h4 class="card-title mt-3 text-center">Estatísticas</h4>
			
				
				<!--  CONTENT HERE -->
				
			</article>
		</div>
		<!-- card.// -->
	</div>

</div>

<!--  

<hr>
<div class="container">
	
	
		<div class="card card-signin my-5" style="border-radius: 1em">
			<article class="card-body mx-auto">
			<h4 class="card-title mt-3 text-center">Informações Gerais</h4>
			<p id="pName">Olá, <strong> ${user.name}</strong>! </p>
			<p>Você possui ${tListas} listas no total </p>
			<p>Você comprou ${nProdutosComprados} produtos de um total de ${tProdutos} </p>
			<p>Você já gastou ${tGasto} </p>
			<p>Você pretende gastar ${tEstipulado} </p>
			</article>
			</div>
			</div>
			
			
			 -->
		


<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>