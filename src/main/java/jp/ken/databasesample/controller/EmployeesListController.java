package jp.ken.databasesample.controller;

import java.util.ArrayList;
import java.util.List;

import jp.ken.databasesample.dao.EmployeesDAO;
import jp.ken.databasesample.entity.Employees;
import jp.ken.databasesample.model.EmployeesModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmployeesListController {
	
	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
	@SuppressWarnings("unchecked")
	private static EmployeesDAO<Employees> empDAO = (EmployeesDAO<Employees>)context.getBean("employeesDAO");

	@Autowired
	private ConditionsCheckValidator conditionsCheckValidator;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setValidator(conditionsCheckValidator);
	}
	
	@RequestMapping(value="/empList", method=RequestMethod.GET)
	public String empList(Model model) {
		List<Employees> list = empDAO.allList();
		model.addAttribute("message", "従業員リスト");
		model.addAttribute("employeesList", list);
		return "listEmployeesData";
	}
	
	@RequestMapping(value="/empSelect", method=RequestMethod.GET)
	public String emp(Model model) {
		EmployeesModel eModel = new EmployeesModel();
		model.addAttribute("employeesModel", eModel);
		model.addAttribute("message", "検索したい従業員番号を入力してください");
		return "selectEmployeesData";
	}
	
	@RequestMapping(value="/empSelect", method=RequestMethod.POST)
	public String empDisp(@Validated @ModelAttribute EmployeesModel eModel, BindingResult result, Model model) {
		model.addAttribute("message", "従業員リスト");
		List<Employees> list = null;
		if (!result.hasErrors()) {
			if (!eModel.getEmployee_id().equals("") && eModel.getEmployee_name().equals("")) {
				int empId = Integer.parseInt(eModel.getEmployee_id());
				Employees emp = empDAO.getById(empId);
				list = new ArrayList<Employees>();
				list.add(emp);
			} else if (eModel.getEmployee_id().equals("") && !eModel.getEmployee_name().equals("")) {
				list = empDAO.getByName(eModel.getEmployee_name());
			} else {
				list = empDAO.allList();
			}
			model.addAttribute("employeesSelectList", list);
		}
		return "selectEmployeesData";
	}
}