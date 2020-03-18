package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipiecoles.java.java350.exception.EmployeException;

public class EmployeTest {

	// 2 ans avant aujourd'hui => 2 années d'ancienneté

	@Test
	public void testAcnienneteDateEmbaucheNmoins2() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().minusYears(2));

		// when
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(2);
	}

	// Date dans le futur => 0

	@Test
	public void testAcnienneteDateEmbaucheNplus2() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(LocalDate.now().plusYears(2));

		// when
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}
	// Date aujourd'hui => 0

	@Test
	public void testAcnienneteDateEmbaucheAujourdhui() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);

		// when
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

	// Date NUll => 0

	@Test
	public void testAcnienneteDateEmbaucheNull() {

		// Given
		Employe employe = new Employe();
		employe.setDateEmbauche(null);

		// when
		Integer nbAnnee = employe.getNombreAnneeAnciennete();

		// Then
		Assertions.assertThat(nbAnnee).isEqualTo(0);
	}

	// test paramétré
	@ParameterizedTest(name = "immat {0} est valide : {4}")
	@CsvSource({ "'M65222',,2,1,1900", "'T52200',1,3,1,1300 ", "'C12335',4,10,1,5300", "'M54871',,5,1,2200 ",
			"'T54112',,2,1,1200 ", "'C78522',3,1,1,3400", "'T12345',1,0,1,1000 ", "'T12345',1,0,0.5,500 ",
			"'M12345',1,0,1,1700 ", ",1,0,1,1000 ", })

	public void testCheckPrimeAnnuelle(String immat, Integer performance, Integer NbAnneeAncienite, Double tempPartiel,
			Double prime) {
		// Given
		Employe employe = new Employe();
		employe.setTempsPartiel(tempPartiel);
		employe.setMatricule(immat);
		employe.setDateEmbauche(LocalDate.now().minusYears(NbAnneeAncienite));
		employe.setPerformance(performance);

		// when
		Double primecalcule = employe.getPrimeAnnuelle();

		// Then
		Assertions.assertThat(primecalcule).isEqualTo(prime);
	}

	@ParameterizedTest(name = "augmentation reussie: salaire initiale : {0} et la salaire actuelle: {2}")
	@CsvSource({ "1800,0.25,2250 ", "2200,0,2200", "2000,1.1,2000", "1700,-0.7,1700 " })

	public void testAugmenterSalaire(Double salaire, double pourcentage, Double salaireFinale) throws EmployeException {

		// Given
		Employe employe = new Employe();
		employe.setSalaire(salaire);

		// when
		employe.augmenterSalaire(pourcentage);
		Double salaireAfterAugmentation = employe.getSalaire();

		// Then
		Assertions.assertThat(salaireAfterAugmentation).isEqualTo(salaireFinale);
	}

	@ParameterizedTest(name = "Rtt année{0}")
	@CsvSource({ "2019,11", "2020,10", "2021,11", "2022,10", "2023,8", "2028,8", "2032,12", "2044,10" })
	public void testgetNbRtt(Integer annee, Integer nbRtt) throws EmployeException {
		// Given
		Employe employe = new Employe();
		Integer nbannee = annee - LocalDate.now().getYear();
		LocalDate nouvelledate = (nbannee < 0) ? LocalDate.now().minusYears(nbannee)
				: LocalDate.now().plusYears(nbannee);

		// when
		Integer nbrtt = employe.getNbRtt(nouvelledate);

		// Then
		Assertions.assertThat(nbrtt).isEqualTo(nbRtt);
	}

	@Test
	public void calculeRttNullTest() {
		Employe employe = new Employe();
		Assertions.assertThatThrownBy(() -> {
			employe.getNbRtt(null);
		}).isInstanceOf(EmployeException.class).hasMessage("La Date ne peut pas être null");
	}

}
