/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.modelo.dto;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Objects;

/**
 * Arquivo do projeto SiafiModelos criando em 30/07/2013
 *
 * @author ari
 */
public class CusteioDto implements Serializable {

    private UnidadeOrcamentaria unidadeOrcamentaria;
    private BigDecimal janeiro;
    private BigDecimal fevereiro;
    private BigDecimal marco;
    private BigDecimal abril;
    private BigDecimal maio;
    private BigDecimal junho;
    private BigDecimal julho;
    private BigDecimal agosto;
    private BigDecimal setembro;
    private BigDecimal outubro;
    private BigDecimal novembro;
    private BigDecimal dezembro;
    private List<SolicitacaoFinanceira> solicitacaoFinanceiras;

    public List<SolicitacaoFinanceira> getSolicitacaoFinanceiras() {
        return solicitacaoFinanceiras;
    }

    public void setSolicitacaoFinanceiras(List<SolicitacaoFinanceira> solicitacaoFinanceiras) {
        this.solicitacaoFinanceiras = solicitacaoFinanceiras;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public BigDecimal getJaneiro() {
        return janeiro;
    }

    public void setJaneiro(BigDecimal janeiro) {
        this.janeiro = janeiro;
    }

    public BigDecimal getFevereiro() {
        return fevereiro;
    }

    public void setFevereiro(BigDecimal fevereiro) {
        this.fevereiro = fevereiro;
    }

    public BigDecimal getMarco() {
        return marco;
    }

    public void setMarco(BigDecimal marco) {
        this.marco = marco;
    }

    public BigDecimal getAbril() {
        return abril;
    }

    public void setAbril(BigDecimal abril) {
        this.abril = abril;
    }

    public BigDecimal getMaio() {
        return maio;
    }

    public void setMaio(BigDecimal maio) {
        this.maio = maio;
    }

    public BigDecimal getJunho() {
        return junho;
    }

    public void setJunho(BigDecimal junho) {
        this.junho = junho;
    }

    public BigDecimal getJulho() {
        return julho;
    }

    public void setJulho(BigDecimal julho) {
        this.julho = julho;
    }

    public BigDecimal getAgosto() {
        return agosto;
    }

    public void setAgosto(BigDecimal agosto) {
        this.agosto = agosto;
    }

    public BigDecimal getSetembro() {
        return setembro;
    }

    public void setSetembro(BigDecimal setembro) {
        this.setembro = setembro;
    }

    public BigDecimal getOutubro() {
        return outubro;
    }

    public void setOutubro(BigDecimal outubro) {
        this.outubro = outubro;
    }

    public BigDecimal getNovembro() {
        return novembro;
    }

    public void setNovembro(BigDecimal novembro) {
        this.novembro = novembro;
    }

    public BigDecimal getDezembro() {
        return dezembro;
    }

    public void setDezembro(BigDecimal dezembro) {
        this.dezembro = dezembro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.unidadeOrcamentaria);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CusteioDto other = (CusteioDto) obj;
        if (!Objects.equals(this.unidadeOrcamentaria, other.unidadeOrcamentaria)) {
            return false;
        }
        return true;
    }

    public CusteioDto() {
        this.janeiro = BigDecimal.ZERO;
        this.fevereiro = BigDecimal.ZERO;
        this.marco = BigDecimal.ZERO;
        this.abril = BigDecimal.ZERO;
        this.maio = BigDecimal.ZERO;
        this.junho = BigDecimal.ZERO;
        this.julho = BigDecimal.ZERO;
        this.agosto = BigDecimal.ZERO;
        this.setembro = BigDecimal.ZERO;
        this.outubro = BigDecimal.ZERO;
        this.novembro = BigDecimal.ZERO;
        this.dezembro = BigDecimal.ZERO;
    }

    public void calculaTotal() {
        for (SolicitacaoFinanceira solicitacaoFinanceira : solicitacaoFinanceiras) {
            switch (solicitacaoFinanceira.getMesCompetencia()) {
                case Janeiro:
                    this.janeiro = janeiro.add(solicitacaoFinanceira.getValor());
                    break;
                case Fevereiro:
                    this.fevereiro = fevereiro.add(solicitacaoFinanceira.getValor());
                    break;
                case Março:
                    this.marco = marco.add(solicitacaoFinanceira.getValor());
                    break;
                case Abril:
                    this.abril = abril.add(solicitacaoFinanceira.getValor());
                    break;
                case Maio:
                    this.maio = maio.add(solicitacaoFinanceira.getValor());
                    break;
                case Junho:
                    this.junho = junho.add(solicitacaoFinanceira.getValor());
                    break;
                case Julho:
                    this.julho = julho.add(solicitacaoFinanceira.getValor());
                    break;
                case Agosto:
                    this.agosto = agosto.add(solicitacaoFinanceira.getValor());
                    break;
                case Setembro:
                    this.setembro = setembro.add(solicitacaoFinanceira.getValor());
                    break;
                case Outubro:
                    this.outubro = outubro.add(solicitacaoFinanceira.getValor());
                    break;
                case Novembro:
                    this.novembro = novembro.add(solicitacaoFinanceira.getValor());
                    break;
                case Dezembro:

                    this.dezembro = dezembro.add(solicitacaoFinanceira.getValor());
                    break;
                default:
                    break;
            }
        }
    }
    public BigDecimal getTotal(){
        BigDecimal tol;
        tol = janeiro.add(fevereiro).add(marco).add(abril).add(maio).add(junho).add(julho).add(agosto).add(setembro).add(outubro).add(novembro).add(dezembro);
        return tol;
    }
}
