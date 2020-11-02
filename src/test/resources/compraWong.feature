Feature: compra Wong, navega al sitio web de Wong, busca un producto y lo añade al carrito de compras, valida que se agrego
  correctamente

  Scenario Outline: Login functionality exists
    Given Que el usuario quiere comprar un producto
    When el busca el <producto> de interes:
    And el agrega el <producto> al carrito de compras
    Then el deberia observar el <producto> en el carrito de productos
  Examples:
    |producto|
    |"Pan Pita Linaza Bimbo Bolsa 10 Unidades"|