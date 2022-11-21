class Decimal {

    static toString(valor) {
        valor = valor || 0
        return valor.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    }

    static toString(valor, casasDecimais) {
        valor = valor || 0
        return valor.toLocaleString('pt-BR', { minimumFractionDigits: casasDecimais, maximumFractionDigits: casasDecimais });
    }

    static fromString(valor) {
        var saida = parseFloat(valor.toString().replace(/\./gi, '').replace(',', '.'));

        return isNaN(saida) ? 0 : saida;

    }

}