package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.ConvenioController;
import br.com.siafi.controller.CredorController;
import br.com.siafi.modelo.AditivoConvenio;
import br.com.siafi.modelo.Convenio;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class ConvenioMB extends BeanGenerico<Convenio> implements Serializable {

    @EJB
    private ConvenioController controller;
    @EJB
    private CredorController credorControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    private Convenio convenio;
    private List<Convenio> lista;
    private AditivoConvenio aditivoConvenio;
    private List<AditivoConvenio> aditivoConvenioList;
    private String nomecredor;
    private List<Credor> credores;

    public ConvenioMB() {
        super(Convenio.class);
    }

    @PostConstruct
    private void init() {
        convenio = (Convenio) beanNavegacao.getRegistroMapa("convenio", new Convenio());
        if (convenio.getId() == null) {
            novo();
        } else {
            try {
                aditivoConvenioList = controller.getAditivos(convenio);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("erro ao listar aditivos", ex, nomecredor);
            }
        }
        lista = new ArrayList<>();
        credores = new ArrayList<>();
    }

    public void salvar() {
        try {
            if (valida()) {
                controller.salvarouAtualizar(convenio);
                MenssagemUtil.addMessageInfo("Convênio salvo com sucesso!");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
            Logger.getLogger(ConvenioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listar() {
        lista = listar(controller);
    }

    public void novo() {
        convenio = new Convenio();
        convenio.setDataInicio(new Date());
        aditivoConvenioList = new ArrayList<>();
    }

    private void addAditivo() {
        if (!aditivoConvenioList.contains(aditivoConvenio)) {
            aditivoConvenioList.add(aditivoConvenio);
        }
    }

    public void novoAditivo() {
        if (aditivoConvenioList == null) {
            aditivoConvenioList = new ArrayList<>();
        }
        aditivoConvenio = new AditivoConvenio();
        aditivoConvenio.setDataCadastro(new Date());
        aditivoConvenio.setConvenio(convenio);
        if (aditivoConvenioList.size() < 10) {
            aditivoConvenio.setNumero("0" + aditivoConvenioList.size());
        } else {
            aditivoConvenio.setNumero(Integer.toString(aditivoConvenioList.size()));
        }
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public List<Convenio> getLista() {
        return lista;
    }

    public void setLista(List<Convenio> lista) {
        this.lista = lista;
    }

    public AditivoConvenio getAditivoConvenio() {
        return aditivoConvenio;
    }

    public void setAditivoConvenio(AditivoConvenio aditivoConvenio) {
        this.aditivoConvenio = aditivoConvenio;
    }

    public String getNomecredor() {
        return nomecredor;
    }

    public void setNomecredor(String nomecredor) {
        this.nomecredor = nomecredor;
    }

    public List<Credor> getCredores() {
        return credores;
    }

    public void setCredores(List<Credor> credores) {
        this.credores = credores;
    }

    public void listarCredores() {
        try {
            credores = credorControler.listarOutros("pessoa.nome", nomecredor);
            if (credores.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum credor encontrado");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    private boolean valida() {
        if (convenio.getCredor() == null) {
            MenssagemUtil.addMessageErro("Informe o credor");
            return false;
        }
        return true;
    }

    public void salvarAditivo() {
        try {
            if (validaAditivo(aditivoConvenio)) {
                controller.salvarouAtualizar(aditivoConvenio);
                MenssagemUtil.addMessageInfo("Convênio salvo com sucesso!");
                addAditivo();
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar aditivo", ex, this.getClass().getName());
        }
    }

    public boolean isEditarAditivo(AditivoConvenio a) {
        if (aditivoConvenioList.size() < 10) {
            return a.getNumero().equals("0" + (aditivoConvenioList.size() - 1));
        } else {
            return a.getNumero().equals(Integer.toString((aditivoConvenioList.size() - 1)));
        }
    }

    private boolean validaAditivo(AditivoConvenio a) throws Exception {
        for (AditivoConvenio aditivo : aditivoConvenioList) {
            if (!aditivoConvenioList.contains(aditivo)) {
                if (aditivo.getDataFinal().after(a.getDataCadastro())) {
                    MenssagemUtil.addMessageWarn("Informe uma data de validade maior do que a data do último aditivo cadastrado.");
                    return false;
                }
            }
        }
        if (!controller.validaAditivo(a)) {
            MenssagemUtil.addMessageWarn("Informe uma data de validade maior do que a data do último aditivo cadastrado.");
            return false;
        }
        return true;

    }

    public List<AditivoConvenio> getAditivoConvenioList() {
        return aditivoConvenioList;
    }

    public void setAditivoConvenioList(List<AditivoConvenio> aditivoConvenioList) {
        this.aditivoConvenioList = aditivoConvenioList;
    }
}
