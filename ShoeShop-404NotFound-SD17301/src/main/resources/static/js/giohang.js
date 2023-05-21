 type="text/javascript"
    var quantity = 1;
    var price = 499000 ;

    function incrementQuantity() {
        quantity++;
        document.getElementById('quantity').innerHTML = quantity;
        document.getElementById('price').innerHTML = quantity * price;
    }

    function decrementQuantity() {
        if (quantity > 1) {
            quantity--;
            document.getElementById('quantity').innerHTML = quantity;
            document.getElementById('price').innerHTML = quantity * price;
        }
    }
