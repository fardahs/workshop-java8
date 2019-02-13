package java8.ex02;

import java8.data.Account;
import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 02 - Map
 */
public class Lambda_02_Test {

	// tag::PersonToAccountMapper[]
	interface PersonToAccountMapper {
		Account map(Person p);
	}

	interface PersonToFirstName{
		String map(Account a);
	}
	// end::PersonToAccountMapper[]

	// tag::map[]
	private List<Account> map(List<Person> personList, PersonToAccountMapper mapper) {
		// TODO implémenter la méthode
		List<Account> account = new ArrayList<Account>();

		for(Person p : personList){

			account.add(mapper.map(p));
		}

		return account;
	}
	// end::map[]

	private List<String> map1(List<Account> personList, PersonToFirstName mapper){
		List<String> personne = new ArrayList<String>();

		for(Account a : personList){

			personne.add(mapper.map(a));
		}

		return personne;
	}

	// tag::test_map_person_to_account[]
	@Test
	public void test_map_person_to_account() throws Exception {

		List<Person> personList = Data.buildPersonList(100);


		// TODO transformer la liste de personnes en liste de comptes
		// TODO tous les objets comptes ont un solde à 100 par défaut
		List<Account> result = map(personList, person -> {
			//Inicialisation de account
			Account account = new Account();
			//Soldes des comptes a 100
			account.setOwner(person);
			account.setBalance(100);
			return account;
		});

		assertThat(result, hasSize(personList.size()));
		assertThat(result, everyItem(hasProperty("balance", is(100))));
		assertThat(result, everyItem(hasProperty("owner", notNullValue())));
	}
	// end::test_map_person_to_account[]

	// tag::test_map_person_to_firstname[]
	@Test
	public void test_map_person_to_firstname() throws Exception {

		List<Person> personList = Data.buildPersonList(100);

		List<Account> accountList = map (personList, person -> {
			//Inicialisation de account
			Account account = new Account();
			account.setOwner(person);
			return account;

		});

		// TODO transformer la liste de personnes en liste de prénoms
		List<String> result = map1(accountList, a ->  a.getOwner().getFirstname());

		assertThat(result, hasSize(personList.size()));
		assertThat(result, everyItem(instanceOf(String.class)));
		assertThat(result, everyItem(startsWith("first")));
	}
	// end::test_map_person_to_firstname[]
}
