
package br.com.psystems.crud.command;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;


public interface ICommand extends Serializable {

	public String execute(HttpServletRequest request);
}
