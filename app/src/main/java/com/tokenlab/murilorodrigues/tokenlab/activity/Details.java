package com.tokenlab.murilorodrigues.tokenlab.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.tokenlab.murilorodrigues.tokenlab.R;
import com.tokenlab.murilorodrigues.tokenlab.model.People;


/**
 * Created by murilo.rodrigues on 03/02/2016.
 * Activity para exibição dos detalhes da entidade People
 */
public class Details extends Activity {

	private EditText edtName;
	private EditText edtSurname;
	private EditText edtAge;
	private EditText edtPhone;
	private People people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.detail);

		people = (People) getIntent().getSerializableExtra("people");

		edtName = (EditText) findViewById(R.id.edtName);
		edtSurname = (EditText) findViewById(R.id.edtSurname);
		edtAge = (EditText) findViewById(R.id.edtAge);
		edtPhone = (EditText) findViewById(R.id.edtPhone);

		edtName.setText(people.getName());
		edtSurname.setText(people.getSurname());
		edtAge.setText(String.valueOf(people.getAge()));
		edtPhone.setText(people.getPhoneNumber());

	}

}
