package sistemafenicia.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class MessagesUtil {

	private MessagesUtil() {
	}

	public static void notice(String title, String texto, String color, long tempo){
		RequestContext.getCurrentInstance().execute("notice('"+title+"','"+texto+"','"+color+"',"+tempo+",'blop');");
	}

	public static void growl(String tipo, String summary) {
		if(tipo.equals("INFO")){
			FacesContext.getCurrentInstance().addMessage("growl",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informa��o", summary));
		}else if(tipo.equals("FATAL")){
			FacesContext.getCurrentInstance().addMessage("growl",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", summary));
		}else if(tipo.equals("ERROR")){
			FacesContext.getCurrentInstance().addMessage("growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", summary));
		}else if(tipo.equals("WARN")){
			FacesContext.getCurrentInstance().addMessage("growl",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", summary));
		}

	}

	public static void messageInfo(String summary) {
		FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}

	public static void messageErro(String summary) {
		FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
	}

	public static void message(String summary, String erro) {
		if(erro==null) {
			FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
		} else {
			FacesContext.getCurrentInstance().addMessage("msgs",new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
		}
	}

	public static void error(String summary) {
		RequestContext.getCurrentInstance().execute("notice('ERRO!','"+summary+"','vermelho',10000,'bling1');");
	}

	public static void sucess(String summary) {
		RequestContext.getCurrentInstance().execute("notice('SUCESSO!','"+summary+"','verde',9000,'blop');");
	}

	public static void info(String summary) {
		RequestContext.getCurrentInstance().execute("notice('INFO!','"+summary+"','azul',9000,'boop2');");
	}

	public static void del(String summary) {
		RequestContext.getCurrentInstance().execute("notice('EXCLUÍDO!','"+summary+"','preto',10000,'beep2');");
	}

	public static void warn(String summary) {
		RequestContext.getCurrentInstance().execute("notice('WARN!','"+summary+"','laranja',10000,'boop3');");
	}

	public static void fatal(String summary) {
		RequestContext.getCurrentInstance().execute("notice('ERRO FATAL!','"+summary+"','vermelho',10000,'blop');");
	}

}
