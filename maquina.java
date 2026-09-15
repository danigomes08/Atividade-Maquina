public class MaquinaDeLavar {

    // criei esse enum pra representar os estados que a maquina pode assumir
    // achei mais facil controlar assim do que ficar usando varios booleans separados
    public enum Estado {
        DESLIGADA,
        LIGADA,
        LAVANDO,
        PAUSADA,
        LAVAGEM_CONCLUIDA,
        CENTRIFUGANDO,
        CENTRIFUGACAO_CONCLUIDA
    }

    private Estado estado;
    private boolean tampaFechada; // controle separado do estado principal, pra tampa aberta/fechada

    public MaquinaDeLavar() {
        // toda maquina comeca desligada e com a tampa fechada
        this.estado = Estado.DESLIGADA;
        this.tampaFechada = true;
    }

    // liga a maquina, so pode se ela ainda nao tiver ligada
    public void ligar() {
        if (estado == Estado.DESLIGADA) {
            estado = Estado.LIGADA;
            System.out.println("Maquina ligada.");
        } else {
            System.out.println("A maquina ja esta ligada.");
        }
    }

    // desliga a maquina
    public void desligar() {
    if (estado == Estado.DESLIGADA) {
        System.out.println("A maquina ja esta desligada.");
    } else if (estado == Estado.LAVANDO || estado == Estado.CENTRIFUGANDO) {
        System.out.println("Nao e possivel desligar a maquina nesse estado: " + estado);
    } else {
        estado = Estado.DESLIGADA;
        System.out.println("Maquina desligada.");
    }
    }

    // abre a tampa, mas nao pode se ta lavando ou centrifugando
    public void abrirTampa() {
        if (estado == Estado.LAVANDO || estado == Estado.CENTRIFUGANDO) {
            System.out.println("Nao da pra abrir a tampa enquanto a maquina esta " + estado);
        } else {
            tampaFechada = false;
            System.out.println("Tampa aberta.");
        }
    }

    // fecha a tampa
    public void fecharTampa() {
        tampaFechada = true;
        System.out.println("Tampa fechada.");
    }

    // inicia a lavagem, so da pra fazer isso com a maquina ligada (parada) e tampa fechada
    public void iniciarLavagem() {
        if (estado != Estado.LIGADA) {
            System.out.println("So da pra iniciar lavagem com a maquina ligada e parada.");
        } else if (!tampaFechada) {
            System.out.println("Feche a tampa antes de iniciar a lavagem.");
        } else {
            estado = Estado.LAVANDO;
            System.out.println("Lavagem iniciada.");
        }
    }

    // pausa a lavagem que ta rolando
    public void pausarLavagem() {
        if (estado == Estado.DESLIGADA) {
            System.out.println("Maquina desligada nao pode ser pausada.");
        } else if (estado != Estado.LAVANDO) {
            System.out.println("So da pra pausar quando esta lavando.");
        } else {
            estado = Estado.PAUSADA;
            System.out.println("Lavagem pausada.");
        }
    }

    // retoma uma lavagem que tava pausada
    public void retomarLavagem() {
        if (estado == Estado.PAUSADA) {
            estado = Estado.LAVANDO;
            System.out.println("Lavagem retomada.");
        } else {
            System.out.println("Nao tem lavagem pausada pra retomar.");
        }
    }

    // conclui a lavagem
    public void concluirLavagem() {
        if (estado == Estado.LAVANDO) {
            estado = Estado.LAVAGEM_CONCLUIDA;
            System.out.println("Lavagem concluida.");
        } else {
            System.out.println("Nao ha lavagem em andamento para concluir.");
        }
    }

    // inicia a centrifugacao, so depois que a lavagem foi concluida
    public void iniciarCentrifugacao() {
        if (estado != Estado.LAVAGEM_CONCLUIDA) {
            System.out.println("So pode centrifugar depois que a lavagem terminar.");
        } else {
            estado = Estado.CENTRIFUGANDO;
            System.out.println("Centrifugacao iniciada.");
        }
    }

    public void concluirCentrifugacao() {
        if (estado == Estado.CENTRIFUGANDO) {
            estado = Estado.CENTRIFUGACAO_CONCLUIDA;
            System.out.println("Centrifugacao concluida.");
        } else {
            System.out.println("Nao ha centrifugacao em andamento para concluir.");
        }
    }

    public Estado getEstado() {
        return estado;
    }

    public boolean isTampaFechada() {
        return tampaFechada;
    }

        public static void main(String[] args) {
        MaquinaDeLavar maquina = new MaquinaDeLavar();

        // A ideia da main e provar que a maquina de estados funciona:
        // cada acao valida muda o estado, e cada acao invalida e bloqueada.
        // Por isso alternamos casos que devem dar certo com casos que devem falhar.

        maquina.ligar();
        maquina.ligar();                  // erro esperado: ja esta ligada

        maquina.abrirTampa();
        maquina.iniciarLavagem();         // erro esperado: tampa aberta
        maquina.fecharTampa();
        maquina.iniciarLavagem();         // ok: LIGADA + tampa fechada -> LAVANDO

        maquina.abrirTampa();             // erro esperado: esta lavando
        maquina.desligar();               // erro esperado: esta lavando

        maquina.pausarLavagem();          // ok: LAVANDO -> PAUSADA
        maquina.pausarLavagem();          // erro esperado: ja esta pausada
        maquina.retomarLavagem();         // ok: PAUSADA -> LAVANDO

        maquina.concluirLavagem();        // ok: LAVANDO -> LAVAGEM_CONCLUIDA
        maquina.iniciarCentrifugacao();   // ok: LAVAGEM_CONCLUIDA -> CENTRIFUGANDO
        maquina.abrirTampa();             // erro esperado: esta centrifugando
        maquina.concluirCentrifugacao();  // ok: CENTRIFUGANDO -> CENTRIFUGACAO_CONCLUIDA

        maquina.abrirTampa();             // ok: ciclo terminou, pode tirar a roupa
        maquina.desligar();               // ok: CENTRIFUGACAO_CONCLUIDA -> DESLIGADA
        maquina.desligar();               // erro esperado: ja esta desligada

        System.out.println("Estado final: " + maquina.getEstado());
    }
}