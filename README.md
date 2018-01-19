# StoryTelling

StoryTelling it's  like a blog for writing collaborative stories. An user can create a new Story 
Proposition, that indicates the main theme of the story, other users can write Story
Fragments in such a way that a story is constructed. The Story Propositions and Story 
Fragments have a maximun lenght of 300 characters.

## How to compile

* You need [maven](https://maven.apache.org/) installed.

* Clone the project and run:
```
cd *projectDirectory*
mvn package
java -jar target/storytelling-0.0.1-SNAPSHOT.jar
```
  The defaul port is `8080`, you can change it using `--server.port=NNNN`
  after `java -jar ...`

## REST API

All the request must be done with the `'Content-Type': 'application/json'` header, and they produce JSON responses. 
Also they return HTTP status codes: 
- `200 OK` if everything gone right.
- `400 BAD REQUEST` if you don't pass the propper parameters.
- `404 NOT FOUND` if an object doesn't exist in the database.
- `409 CONFLICT` if there are existing objects collisions in the database 
  (when you try to create a new user with an already existing username).


| URL                                 | METHOD | Params                                                            | Description                                                                   |
|-------------------------------------|--------|-------------------------------------------------------------------|-------------------------------------------------------------------------------|
| `/api/user/new`                       | POST   | `username(str)`, `firstName(str)`, `lastName(str)`, `email(str)`          | Creates a new user in the database                                            |
| `/api/user/{id(int)}`                      | GET    | -                                                                 | Finds an user by its id                                                       |
| `/api/user/?username=`                | GET    | `username(str)`                                                     | Finds an user by its username                                                 |
| `/api/user/{id(int)}/props`                | GET    | -                                                                 | Find an user's story propositions                                             |
| `/api/user/{id(int)}/update`               | POST   | `id(int)`, `username(str)`, `firstName(str)`, `lastName(str)`, `email(str)` | Update an existing user                                                       |
| `/api/user/all`                       | GET    | -                                                                 | Get all the users                                                             |
| `/api/story/new`                      | POST   | `text(str)`, `userId(int)`                                            | Creates a new Story proposition on the database                               |
| `/api/story/{id(int)}`                     | GET    | -                                                           | Finds a story proposition by its id                                           |
| `/api/story/random`                   | GET    | -                                                                 | Finds a random story proposition                                              |
| `/api/story/?user=`                   | GET    | `user(str)`                                                         | Finds all the story propositions from an user                                 |
| `/api/story/?user_id=`                | GET    | `userId(int)`                                                       | Finds all the story propositions from an user                                 |
| `/api/story/between/?start={}&end={}` | GET    | `start(str: 'yyyy-MM-dd')`, `end(str: 'yyyy-MM-dd')`                   | Finds all the story propositions that where created between `start` and `end` |
| `/api/story/{id(int)}/update`              | POST   | `text(str)`                                                         | Updates an existing story proposition                                         |
| `/api/story/find/?text=`              | GET    | `text(str)`                                                         | Finds a story proposition by it's title (text).                               |
| `/api/frag/new`                       | POST   | `text(str)`, `propId(int)`, `userId(int)`                            | Creates a new Story fragment on the database.                                 |
| `/api/frag/{id(int)}`                      | GET    |                                                                   | Finds a story fragment by its id                                              |
| `/api/frag/{id(int)}/update`               | POST   | `text(str)`                                                         | Update an existing story fragment                                             |

## Notes

* Not all the REST functions have a frontend.
* The database doesn't persist, this project uses [HSQL](http://hsqldb.org/), 
  it creates a new database in memory every time the project is executed.
