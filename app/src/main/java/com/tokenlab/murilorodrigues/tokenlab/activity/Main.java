package com.tokenlab.murilorodrigues.tokenlab.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.tokenlab.murilorodrigues.tokenlab.R;
import com.tokenlab.murilorodrigues.tokenlab.adapter.ListPeopleAdapter;
import com.tokenlab.murilorodrigues.tokenlab.model.People;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by murilo.rodrigues on 03/02/2016.
 * Activity Principal aonde são listados os contatos baixados de um servidor remoto
 */
public class Main extends ListActivity {
	private List<People> mainPeopleList = new ArrayList<>();
	private final String STATE_PEOPLE_LIST = "peopleList";
	private final String URL_SERVER = "http://private-61391-person9.apiary-mock.com/people";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (savedInstanceState != null){
			// Pega a lista de contatos se ela foi salva
			mainPeopleList = (List<People>) savedInstanceState.getSerializable(STATE_PEOPLE_LIST);
		}

		// Executa tarefa em background
		new DownloadJsonAsyncTask()
				.execute(URL_SERVER);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// Entra no detalhamento do Contato ao clicar sobre ele
		super.onListItemClick(l, v, position, id);

		People people = (People) l.getAdapter().getItem(position);

		Intent intent = new Intent(this, Details.class);
		intent.putExtra("people", people);

		startActivity(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Salva a lista de contatos
		outState.putSerializable(STATE_PEOPLE_LIST, (Serializable) mainPeopleList);
	}

	/**
	 * Classe responsável por baixar os dados via JSON de um servidor remoto
	 */
	class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<People>> {
		ProgressDialog dialog;

		// Informa que está sendo feito o download do JSON
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(Main.this, getString(R.string.aguarde),
					getString(R.string.download));
		}

		// Acessa o servico do JSON e retorna a lista de contatos
		@Override
		protected List<People> doInBackground(String... params) {
			String urlString = params[0];
			List<People> peopleList = new ArrayList<People>();
			HttpURLConnection connection = null;
			BufferedReader reader = null;

			// Se há uma lista de contatos salva
			if (mainPeopleList.size() > 0) {

				// Reutiliza os dados para não realizar o download novamente
				peopleList = mainPeopleList;
			}
			else {
				// Faz download dos contatos
				try {
					// Conecta a URL via HTTP
					URL url = new URL(urlString);
					connection = (HttpURLConnection) url.openConnection();
					connection.connect();

					// Recupera informações da URL
					InputStream stream = connection.getInputStream();
					reader = new BufferedReader(new InputStreamReader(stream));
					StringBuffer buffer = new StringBuffer();
					String line = "";

					// Faz um laço para cada linha de string e cria um buffer
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}

					// Cria um vetor de json com o buffer
					JSONArray jsonArray = new JSONArray(buffer.toString());
					JSONObject jsonPeople;

					// Laço para todos os registros da Array Json
					for (int i = 0; i < jsonArray.length(); i++) {

						jsonPeople = new JSONObject(jsonArray.getString(i));

						// Passa as informações de Json para Objeto
						People people = new People();
						people.setId(jsonPeople.getInt("id"));
						people.setName(jsonPeople.getString("name"));
						people.setSurname(jsonPeople.getString("surname"));
						people.setAge(jsonPeople.getInt("age"));
						people.setPhoneNumber(jsonPeople.getString("phoneNumber"));

						peopleList.add(people);

					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {

					if (connection != null) {
						connection.disconnect();
					}
					try {
						if (reader != null) {
							reader.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// Guarda os itens na lista global, pois será utilizada na reconstrução da activity
				mainPeopleList = peopleList;
			}
			// Retorna uma lista de pessoas
			return peopleList;
		}

		@Override
		protected void onPostExecute(List<People> result) {

			super.onPostExecute(result);
			dialog.dismiss();

			// Adiciona os contatos encontrados numa lista
			if (result.size() > 0) {

				ListAdapter adapter = new ListPeopleAdapter(Main.this, result);
				setListAdapter(adapter);

			} else {

				// Avisa que não foi possível acessar os dados
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Main.this)
						.setTitle(R.string.erro)
						.setMessage(R.string.falha)
						.setPositiveButton(R.string.btn_ok, null);
				builder.create().show();
			}
		}
	}
}
