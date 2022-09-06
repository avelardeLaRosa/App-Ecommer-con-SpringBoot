/**
 * 
 */
 function eliminar(id) {
	swal({
		title: "¿Seguro de eliminar producto?",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((OK) => {
			if (OK) {
				$.ajax({
					url:"/productos/delete/" + id,
					success: function(res){
						console.log(res);
					}
				});
				swal("Producto eliminado", {
					icon: "success",
				}).then((ok)=>{
					if(ok){
						location.href="";
					}
				});
			} else {
				swal("Eliminacion cancelada");
			}
		});
}



 function eliminarUsuario(id) {
	swal({
		title: "¿Seguro de eliminar usuario?",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((OK) => {
			if (OK) {
				$.ajax({
					url:"/usuario/eliminar/" + id,
					success: function(res){
						console.log(res);
					}
				});
				swal("Usuario eliminado", {
					icon: "success",
				}).then((ok)=>{
					if(ok){
						location.href="/administrador/usuarios";
					}
				});
			} else {
				swal("Eliminacion cancelada");
			}
		});
}


