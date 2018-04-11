[![Build Status](https://travis-ci.org/svantja/MenschAergerDichNicht.svg?branch=master)](https://travis-ci.org/svantja/de.htwg.se.MenschAergerDichNicht)

[![Coverage Status](https://coveralls.io/repos/github/svantja/MenschAergerDichNicht/badge.svg?branch=master)](https://coveralls.io/github/svantja/MenschAergerDichNicht?branch=master)


Mensch Aerger Dich Nicht
=========================

This is scala project for the class Software Engineering at the University of Applied Science HTWG Konstanz

## Goals of project

* learning Scala
* clean code
* Design Patterns
    * State Pattern (game state)
    * Observer Pattern (for MVC inversion of control)
    * Command Pattern (action & undo)
* MVC Architecture
* Tests & Specs
* Build Tool & Continuous Integration
    * Travis
    * sbt
* Version Control System
    *Git

## Game Overview

**Game preparation**

* Add players
    * `add name`
    * click on button `add player`
    * tokens are set on each players house
* Start Game
    * `start`
    * click on button `start`
    
**Playing the Game**

* signal ready to dice
    * `ready` or `r`
    * click button `dice`
* select token-id to move
    * `move 1`
    * click on token
    
* tokens start on each players start-field

**End of Game**

* Game ends when all players, except of one, have all tokens on their target-fields
* First player to finish wins the game




