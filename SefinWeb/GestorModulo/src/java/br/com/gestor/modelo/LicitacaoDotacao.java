package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "LICITACAODOTACAO", catalog = "", schema = "")
public class LicitacaoDotacao implements Serializable {

    @EmbeddedId
    private DotacaoLicitacaoPK dotacaoLicitacaoPK;
    @Column(name = "LIDEXPORTADO")
    private Boolean exportado;

    public DotacaoLicitacaoPK getDotacaoLicitacaoPK() {
        return dotacaoLicitacaoPK;
    }

    public void setDotacaoLicitacaoPK(DotacaoLicitacaoPK dotacaoLicitacaoPK) {
        this.dotacaoLicitacaoPK = dotacaoLicitacaoPK;
    }

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.dotacaoLicitacaoPK);
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
        final LicitacaoDotacao other = (LicitacaoDotacao) obj;
        if (!Objects.equals(this.dotacaoLicitacaoPK, other.dotacaoLicitacaoPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LicitacaoDotacao{" + "dotacaoLicitacaoPK=" + dotacaoLicitacaoPK + '}';
    }
}
