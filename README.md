# RecoLang

This project is based on a school project for the University of Nantes.

It is a java project wich recognizes different languages. With models for 10 languages, the program build files of bigrams and unigrams with frequencies of apparition. With those files, the program will analyse a sentence and find the language by using probabilities.

The purpose of the project was to run performance tests on files containing 22 000 sentences to recognize.

## Construction

This project is build in two parts : 
- the RecoLang part is used for the tests of performances. It's implementing the main algorithm of the program.
- the LanguageModel part is used for building the modelling of models for the languages. This part will evaluate probabilities and build files of n-grams for each language.

## Languages 

The languages used in the program are :

- Spanish
- Deutsch
- Hungarian
- Italian
- Latvian
- Dutch
- Polish
- Portuguese
- Swedish
- Estonian

The program can be used for building any language model if you used it with a corpus containing sentences of the language.

A part for recognize the unknown languages is also implemented.