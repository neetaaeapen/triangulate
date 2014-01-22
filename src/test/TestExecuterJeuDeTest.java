package test;

import jeuxDeTest.ExecuterJeuDeTest;
import org.junit.Test;

import jeuxDeTest.EnregistrerResultats;

public class TestExecuterJeuDeTest {

	@Test
	public void testJeu() {
		int nbSommets = 8 ;
		String chemin;
		
		chemin = "JeuxDeTest/testGaussienne/" + nbSommets + "_100_1000";
		double[] res = ExecuterJeuDeTest.executerJeu(chemin, true, true, true, true);
		
		System.out.println("\n\nRESULTATS \n\n");
		
		System.out.println("ES avec elagage : ");
		System.out.println("\t tps : " + res[0] + "ms");
		System.out.println("\t nbAppels : " + res[1]);
		
		System.out.println("ES sans elagage : ");
		System.out.println("\t tps : " + res[2] + "ms");
		System.out.println("\t nbAppels : " + res[3]);
		
		System.out.println("Algo Dynamique : ");
		System.out.println("\t tps : " + res[4] + "ms");
		System.out.println("\t nbAppels : " + res[5]);
		
		System.out.println("Glouton : ");
		System.out.println("\t tps : " + res[6] + "ms");
		System.out.println("\t nbAppels : " + res[7]);
		System.out.println("\t nbResSubOptimaux " + res[8]);			// pourcentage 
		System.out.println("\t gloutonDiffResMoyen " + res[9]);
		System.out.println("\t plusGrandeDifference " + res[10]);		
		
		// ajout a un fichier de resultats
		
		chemin = "JeuxDeTest/resultatsGaussienne/";
		EnregistrerResultats.ajoutResultat(chemin+"esE_tps", nbSommets + ";" + res[0] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"esE_nbA", nbSommets + ";" + res[1] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"es_tps", nbSommets + ";" + res[2] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"es_nbA", nbSommets + ";" + res[3] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"dyn_tps", nbSommets + ";" + res[4] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"dyn_nbA", nbSommets + ";" + res[5] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"gl_tps", nbSommets + ";" + res[6] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"gl_nbA", nbSommets + ";" + res[7] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"gl_nbResSub", nbSommets + ";" + res[8] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"gl_diffMoyenne", nbSommets + ";" + res[9] + ";\n");
		EnregistrerResultats.ajoutResultat(chemin+"gl_plusGrandeDiff", nbSommets + ";" + res[10] + ";\n");
	}

	
	
	
}
