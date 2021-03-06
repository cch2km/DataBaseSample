package jp.ken.databasesample.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.databasesample.dao.EmployeesDAO;
import jp.ken.databasesample.entity.Employees;
import jp.ken.databasesample.model.EmployeesModel;

@Controller
@RequestMapping("change")
public class EmployeesChangeController {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
	@SuppressWarnings("unchecked")
	private static EmployeesDAO<Employees> empDAO = (EmployeesDAO<Employees>)context.getBean("employeesDAO");
	
	@RequestMapping(method=RequestMethod.GET)
	public String changeList(Model model) {
		EmployeesModel eModel = new EmployeesModel();
		model.addAttribute("employeesModel", eModel);
		List<Employees> list = empDAO.allList();
		model.addAttribute("changeEmployeesList", list);
		return "changeEmployeesData";
	}
}
