# User Guide

## Introduction

**FlashBang** is a CLI app designed to provide students with a smart way of studying for their modules. The app will manage a limited number of flashcards for a small number of modules, optimized for users who prefer a CLI.

## Target User Profile

NUS students who want to review their modules using flashcards. The user: 

- Has a need to create flashcards for their studies
- Needs to be able to test themselves on flashcard content
- Needs to be able to track how well they understand the modules they take
- Can type fast
- Prefers typing to mouse interactions
- Is used to using CLI applications

## Value Propositions

The app will provide NUS students with a smart way of studying for their modules. The app will manage a limited number of flashcards for a small number of modules, optimized for users who prefer a CLI.

## Quick Start

1. Ensure that you have Java 17 or above installed.

2. Down the latest version of `FlashBang` from [here](https://github.com/AY2425S1-CS2113-T11-2/tp/releases).

3. Copy the jar file into an empty folder.

4. Open a command window in that folder.

5. Run the command java -jar {filename}.jar e.g., java -jar Duke.jar (i.e., run the command in the same folder as the jar file).

## Features

The app allows for creating and managing flashcards each of which contains
a question and an answer. Flashcards are organized into modules. Following 
is a list of command which are supported with examples.

### Adding flashcards: `add`

Add a flashcard to a flashcard set. <br>
Topics are optional fields that are used to enhance organisation
**Note* It is not allowed to have the delimiter " | " in the questions and answers.

```bash
add --m [MODULE NAME] --q [QUESTION] --a [ANSWER]
```
or
```bash
add --m [MODULE NAME] --t [TOPIC NAME] --q [QUESTION] --a [ANSWER]
```

**Examples:**
```bash
add --m CS2113 --q "What is OOP?" --a "Object-Oriented Programming"
add --m CS1010 --q "What is a variable?" --a "A storage location in memory with a name"
add --m MA1521 --q "What is the derivative of sin(x)?" --a "cos(x)"
add --m CS2113 --t OOP --q "What is an Object?" --a "An entity with state and behaviour"
```

### Deleting flashcards: `delete`

To delete one flashcard:

```bash
delete --m [MODULE NAME] --i [INDEX]
```

To delete all flashcards in a set:

```bash
delete --m [MODULE NAME]
```

**Examples:**
```bash
delete --m CS2113 --i 2    # Deletes second flashcard in the module CS2113
delete --m MA1521 --i 5    # Deletes fifth flashcard in the module MA1521
delete --m CS1010          # Deletes all flashcards in the module CS1010
```

### Viewing all flashcards: `view`
Lists all flashcards for every module.
```bash
view --all
```

**Example:**
```bash
view --all
```

### Viewing all flashcards in a module without the answers: `flashbang`

```bash
flashbang --m [MODULE NAME]
```

**Example:**
```bash
flashbang --m CS1010
```
```
Question: "What is a variable?"
Reveal answer? (Q to quit) (Y/N)
Y
Answer: "A storage location in memory with a name." 
Next question: "What is a constant?"
Reveal answer? (Q to quit) (Y/N)
Q
Bye!!
```

### Filter flashcards by module: `view`
```bash
view --m [MODULE NAME]
```

**Example:**
```bash
view --m CS2113
```

### Edit flashcard: `edit`

Edits an existing flashcard.

```bash
edit --m [MODULE NAME] --i [INDEX] --q [NEW QUESTION] --a [NEW ANSWER]
```

Or 

```bash
edit --m [MODULE NAME] --i [INDEX]    # Prompts for inputs
```

**App Prompts:**
```
App: New Question: [NEW QUESTION]
App: New Answer: [NEW ANSWER]
```

**Examples:**
```bash
edit --m CS1010 --i 2 --q "What is a constant?" --a "A value that cannot be changed once initialized."
edit --m MA1521 --i 3 --q "What is the integral of 1/x?" --a "ln|x| + C"

__________________________________________________
> edit --m CS2113 --i 1
Old Question : Question 2
Do you want to change Question (y/n):
> y
Enter new Question :
> What does OOP stand for?
Old Answer : Answer for question 2
Do you want to change Answer (y/n):
> y
Enter new Answer :
> Object-oriented programming
__________________________________________________
Successfully edited flashcard

```

### Search for flashcards in a module by topic or by a search term
Searches for existing flashcards that contain the search term or have topics that contain the search term.
<br>
To search by topic, add the `/t` flag after the module name.
<br>
When searching, the search term is case-sensitive.
```bash
search --m [MODULE NAME] /t --s [SEARCH TERM]
```
*or*
```bash
search --m [MODULE NAME] --s [SEARCH TERM]
```
**Examples:**
```bash
search --m CS2113 --s state
	____________________________________________________________
	1. WHAT is an Object: 
	 An entity with a state and a behaviour 
	 topic: OOP
	
	____________________________________________________________
search --m CS2113 /t --s OOP
	____________________________________________________________
	1. What is OOP: 
	 Object-Oriented Programing 
	 topic: OOP
	2. WHAT is an Object: 
	 An entity with a state and a behaviour 
	 topic: OOP
	
	____________________________________________________________

```
### Quitting the app: `quit`
Quits the app session. 
```bash
quit
```

**Example:**
```bash
quit
```


## Command summary

| Command                                             | Description                                                                      |
|-----------------------------------------------------|----------------------------------------------------------------------------------|
| Add flashcards                                      | ```add --m [Module Name] {--t [Topic] (optional)} --q [Question] --a [Answer]``` |
| Delete one flashcard                                | ```delete --m [MODULE NAME] --i [INDEX]```                                       |
| Delete all flashcards in a set                      | ```delete --m [MODULE NAME]```                                                   |
| View all flashcards in every module                 | ```view --all```                                                                 |
| View all flashcards in a module without the answers | ```flashbang --m [MODULE NAME]```                                                |
| Filter flashcards by module                         | ```view --m [MODULE NAME]```                                                     |
| Edit flashcard                                      | ```edit --m [MODULE NAME] --i [INDEX] --q [NEW QUESTION] --a [NEW ANSWER]```     |
| Search flashcards 				      | ```search --m [MODULE NAME] {/t (optional} --s [SEARCH TERM]```                     |
| Quit the app                                        | ```quit```                                                                       |

Note that specifying multiple command keywords in the input will be understood as command of the first type.

## FAQs
Q: Can I add two flashcards same question but different answer.

A: Yes. Adding the two flashcards with both the same answer and question will work.


