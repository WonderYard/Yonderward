Abbiamo pensato alle classi come un modo per scrivere le regole una sola volta e assegnarle agli stati che devono avere quel comportamento. Abbiamo pensato di sostituirle con il concetto (quasi equivalente) di regola isolata dallo stato per permetterne il riutilizzo. Ma non abbiamo considerato il seguente fatto: una classe può essere vuota, cioè senza regole, ed è utilissima per evitare di scrivere più regole quando devo controllare più stati nel vicinato ma devo applicare in ogni caso sempre la stessa regola (e questo secondo me è una cosa che accade spesso: se più stati mi influenzano allo stesso modo, allora probabilmente si comportano in modo simile tra di loro, rafforzando quindi il concetto di appartenenza a una classe).
Quindi riassumendo una classe serve per raggruppare regole, sia TRA stati con comportamenti simili, sia PER stati che hanno influenze.


Esempio senza classi e con evolve separato da state. Risultato: completamente fuori controllo.

	state A
	state B
	state C
	state X
	state Y
	state Z
	state W


	evolve A to B when X 2 when Y 0 when Z 0
	evolve A to B when Y 2 when Z 0 when X 0
	evolve A to B when Z 2 when X 0 when Y 0

	evolve A to B when X 1 when Y 1 when Z 0
	evolve A to B when Y 1 when Z 1 when X 0
	evolve A to B when Z 1 when X 1 when Y 0


	evolve B to C when X 2 when Y 0 when Z 0
	evolve B to C when Y 2 when Z 0 when X 0
	evolve B to C when Z 2 when X 0 when Y 0

	evolve B to C when X 1 when Y 1 when Z 0
	evolve B to C when Y 1 when Z 1 when X 0
	evolve B to C when Z 1 when X 1 when Y 0


	evolve C to A when X 2 when Y 0 when Z 0
	evolve C to A when Y 2 when Z 0 when X 0
	evolve C to A when Z 2 when X 0 when Y 0

	evolve C to A when X 1 when Y 1 when Z 0
	evolve C to A when Y 1 when Z 1 when X 0
	evolve C to A when Z 1 when X 1 when Y 0


	evolve X to W when A 1 when B 0 when C 0
	evolve X to W when B 1 when C 0 when A 0
	evolve X to W when C 1 when A 0 when B 0


	evolve Y to W when A 1 when B 0 when C 0
	evolve Y to W when B 1 when C 0 when A 0
	evolve Y to W when C 1 when A 0 when B 0


	evolve Z to W when A 1 when B 0 when C 0
	evolve Z to W when B 1 when C 0 when A 0
	evolve Z to W when C 1 when A 0 when B 0


Esempio senza classi con regole dentro la dichiarazione state. Risultato: eccessivo utilizzo dell'algebra booleana.

	state A to B when (X 2 and Y 0 and Z 0)
	               or (Y 2 and Z 0 and X 0)
	               or (Z 2 and X 0 and Y 0)
	               or (X 1 and Y 1 and Z 0)
	               or (Y 1 and Z 1 and X 0)
	               or (Z 1 and X 1 and Y 0)
	
	state B to C when (X 2 and Y 0 and Z 0)
	               or (Y 2 and Z 0 and X 0)
	               or (Z 2 and X 0 and Y 0)
	               or (X 1 and Y 1 and Z 0)
	               or (Y 1 and Z 1 and X 0)
	               or (Z 1 and X 1 and Y 0)
	
	state C to A when (X 2 and Y 0 and Z 0)
	               or (Y 2 and Z 0 and X 0)
	               or (Z 2 and X 0 and Y 0)
	               or (X 1 and Y 1 and Z 0)
	               or (Y 1 and Z 1 and X 0)
	               or (Z 1 and X 1 and Y 0)
	
	state X to W when (A 1 and B 0 and C 0)
	               or (B 1 and C 0 and A 0)
	               or (C 1 and A 0 and B 0)

	state Y to W when (A 1 and B 0 and C 0)
	               or (B 1 and C 0 and A 0)
	               or (C 1 and A 0 and B 0)

	state Z to W when (A 1 and B 0 and C 0)
	               or (B 1 and C 0 and A 0)
	               or (C 1 and A 0 and B 0)
	
	state W


Esempio con le classi. Risultato: :D

	class XYZ to W when ABC 1
	class ABC
	
	state A is ABC to B when XYZ 2
	state B is ABC to C when XYZ 2
	state C is ABC to A when XYZ 2
	
	state X is XYZ
	state Y is XYZ
	state Z is XYZ
	
	state W