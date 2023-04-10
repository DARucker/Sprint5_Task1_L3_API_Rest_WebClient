# Exercise Rest API connected to another Rest API
Access the page ->https://start.spring.io/, and generate a Spring boot project with the following characteristics: Using WebClient, you will need to connect to the API you made in level 2, to call and test all the requests this API allows.

Note, that in this level 3 task, you have no reference to any database, nor do you need to use JPA, as your repository will access the level 2 API.

You don't need to create a View, because this level 3 is planned as a Rest API, but you will need to create all the layers up to the controller like any other application:

- cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n03.controllers

- cat.itacademy.barcelonactiva.surnames.nom.s05.t01.n03.model.domain

- cat.itacademy.barcelonactiva.surnames.name.s05.t01.n03.model.dto

- cat.itacademy.barcelonactiva.surnames.nom.s05.t01.n03.model.services

- cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n03.model.repository



The controller class must be able to handle the following requests:

http://localhost:9002/flor/clientFlorsAdd

http://localhost:9002/flor/clientFlorsUpdate

http://localhost:9002/flor/clientFlorsDelete/{id}

http://localhost:9002/flor/clientFlorsGetOne/{id}

http://localhost:9002/flor/clientFlorsAll

As you can see, in the application.properties file, you must configure that the port to use is 9002.

To test level 3, you'll need to have the level 2 API running. You won't have any problems, since the level 3 API works with port 9002 and the level 2 API with port 9001.
important

You should include swagger so that any developer can get a quick idea of the resources available in the API.
