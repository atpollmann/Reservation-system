================ REAME ================
==== Observaciones sobre entrega 1 ====

Diseño:

- Tu código me parece bastante bueno y preciso. Se nota que has entendido la materia de clase.
- Tengo sin embargo algunos comentarios menores:
- Por qué no usaste herencia para los tipos de jugadores? (la lógica de negocio para calcular el valor de los jugadores no es correcta me parece)
[listo] - Por qué las entidades están en "persistence" ? Las entidades forman parte de la capa de negocios y no de la capa de acceso a datos


Informe:

- En el informe echo de menos las argumentaciones de las decisiones de diseño. Te doy ejemplos:
- [trabajando en esto] Por qué no hay herencia para los tipos de jugadores?
- Por qué el validador tiene los métodos estáticos y no es una clase con una interfaz (los Bean usan la clase directamente, lo que viola el principio "D" de SOLID).
- Por qué decidiste no utilizar enumeraciones?
- No había posibilidad de poner lógica de negocio en las entidades?

Conclusión:
- Repito que tu código es bastante bueno.
- Me parece que las entidades han sido generadas automáticamente, aunque no estoy 100% seguro. El hecho de definir tus entidades como lo has hecho
te ha dificultado el diseño de ciertas partes de tu proyecto (p.ej. el cálculo del valor de los jugadores).
- El informe es pobre es discusión.

Nota: 6,0