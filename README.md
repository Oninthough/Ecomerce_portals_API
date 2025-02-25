In spring mvc I need to make 2 module one will be configurator another one will be used for common portal like infozone so that we can reduce number of controller service and repositories for large number of entities in Configurator, how we can implement this multimodule structure in spring boot I'm using community version of intellij ide give the steps and POM structure and the way to run it properly 
Project structure should be 
serive

/controllers

/repository

/entity

/common-genericRe, commonService

=>

Exertions

package com.configurator.controller;

package com.configurator.service;

Package com.infozone.common.util

Package com.infozone.common.exceptions

Package com.infozone.common.repository.GenericRepo

Package com.infozone.common.service.GeneticService
