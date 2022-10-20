
    
$(document).ready(function () {
    $('.cep').mask("99999-999");
    const selectElement = document.querySelector('.cep');
    
    if(selectElement){
        console.log(selectElement);
        selectElement.addEventListener('change', (event) => {
            pesquisacep(event.target.value);
        });
    }
});


function cep_callback(conteudo) {
    if (!("erro" in conteudo)) {
        
        var codigo_ibge_cidade = conteudo.ibge;
        
        //var codigo_ibge_cidade = codigo_ibge.substring(2, codigo_ibge.length);

        document.getElementById('numero').focus();
        document.getElementById('endereco').value = (conteudo.logradouro);
        document.getElementById('bairro').value = (conteudo.bairro);
        document.getElementById('cidade').value = (conteudo.localidade);
        document.getElementById('uf').value = (conteudo.uf);
        document.getElementById('codigoMunicipio').value = (codigo_ibge_cidade);

    } //end if.
    else {
        //CEP não Encontrado.
        alert("CEP não encontrado.");
    }
}
function pesquisacep(valor) {

    //Nova variável "cep" somente com dígitos.
    var cep = valor.replace(/\D/g, '');
    //Expressão regular para validar o CEP.
    var validacep = /^[0-9]{8}$/;
    //Valida o formato do CEP.
    if (validacep.test(cep)) {

        //Cria um elemento javascript.
        var script = document.createElement('script');

        //Sincroniza com o callback.
        script.src = 'https://viacep.com.br/ws/' + cep + '/json/?callback=cep_callback';

        //Insere script no documento e carrega o conteúdo.
        document.body.appendChild(script);

    } //end if.
    else {
        alert("Formato de CEP inválido.");
    }
};