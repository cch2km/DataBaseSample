package jp.ken.databasesample.controller;

import jp.ken.databasesample.model.EmployeesModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ConditionsCheckValidator implements Validator {

	@Autowired
	private Validator validator;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return EmployeesModel.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.invokeValidator(validator, target, errors);
		EmployeesModel eMolde = (EmployeesModel)target;
		String id = eMolde.getEmployee_id();
		String name = eMolde.getEmployee_name();
		if (id != null && name != null && !id.equals("") && !name.equals("")) {
			errors.rejectValue("employee_id", 
					"ConditionsCheckValidator.employeesModel.employee_id", 
					"入力する場合は、IDか名前のどちらかに入力して下さい。");
		}
		if (id != null && !id.equals("")) {
			if(!id.matches("[0-9]+")) {
				errors.rejectValue("employee_id", 
						"ConditionsCheckValidator.employeesModel.employee_id", 
						"IDを入力する場合は、数値を入力して下さい。");
			}
		}
	}
}
