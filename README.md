# Novel Generator
This computer program feeds on the publicly available works of different authors and will attempt to author another book similar to the inspiration.

### Training Model

The model is a map data structure that maps a `String` to a `List<String>`; the map key is a k-token phrase of words/tokens and the map value is all of the different k-token phrases of words/tokens that follow the key in the training texts. For example, with a k of 1, if the words `dog runs` occur together in your training text, the model map will contain an element with a key of `dog`, whose value is a list that contains the word `runs`, and the map will contain an element with the key of `runs` with the value of whatever directly follows.

### Standards Met

- **Code structure**
    - No code copied many times within a method, many times in a class, etc.
    - Code is structured in an easy-to-read way
    - Properly handles user input validation
    - Architecture is well-formed and follows the single responsibility principle
- **Testing**
    - All tests run and pass
    - All assert statements have a unique "message" passed to them
- **Software engineering principals**
    - No linter errors
    - Well-chosen variable and method names
- **Data Structures**
    - Appropriate choices for dynamic types of `Map` and `List` in model
- **Algorithms**
    - Demonstrates knowledge of algorithms, as taught in class


**Example. If your training text was the following:**

> _The dog went to the dog groomer in the PetsMart close to The Nebraska Crossing Outlets. The dog is clean._

**The model might look something like this (k=1):**

Model Key (String)  | Model Value (List of Strings)
----------- | ------
`.`         | `The` ,`The`
`The`       | `dog`, `Nebraska`, `dog`
`dog`       | `went`, `groomer`, `is`
`went`      | `to`
`to`        | `the`, `The`
`the`       | `dog`, `PetSmart`
`groomer`   | `in`
`in`        | `the`
`PetSmart`  | `close`
`close`     | `to`
`Nebraska`  | `Crossing`
`Crossing`  | `Outlets`
`Outlets`   | `.`
`is`        | `clean`
`clean`     | `.`

**The same model instead with k=2:**

Model Key (String)  | Model Value (List of Strings)
------------------- | ------
`The dog`           | `went to`, `is clean`
`dog went`          | `to the`
`went to`           | `the dog`
`to the`            | `dog groomer`
`the dog`           | `groomer in`
`dog groomer`       | `in the`
`groomer in`        | `the PetsMart`
`in the`            | `PetsMart close`
`the PetsMart`      | `close to`
`PetsMart close`    | `to The`
`close to`          | `The Nebraska`
`to The`            | `Nebraska Crossing`
`The Nebraska`      | `Crossing Outlets`
`Nebraska Crossing` | `Outlets .`
`Crossing Outlets`  | `. The`
`Outlets .`         | `The dog`
`. The`             | `dog is`, `dog went`
`dog is`            | `clean .`
`is clean`          | `. The`
`clean .`           | `The dog`

### Program Input and Output

Generates a novel of a user-specified minimum length of tokens.

Once novel is generated based on a requested length, it continues auto-generating additional text until the next end-of-sentence punctuation is reached (a period, question mark, or exclamation point).


#### Caveats Due to Tokenization 

Because periods are their own token and because apostrophes are considered delineators, program output often looks like this:

```
I walked my dog to the car . I didn t see 
//       extra space -----^        ^---- another extra space  
```
