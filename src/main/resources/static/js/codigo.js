/**
 * 
 */
 
 function AlignetVPOS2(){
	
	if(AlignetVPOS2.isSafari()){
		var urlBase = 'https://vpayment.verifika.com'
		var context = 'VPOS2'
		
		var win = window.open(urlBase+context+'/faces/pages/safariEntry.xhtml','_blank','height=100px,width=100px,top=9999px,left=9999px')
		
		setTimeout(function(){
			win.close
			
		},2000)
	}
	
	

	
	
	
}

