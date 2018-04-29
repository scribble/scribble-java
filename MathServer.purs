-- module Scribble.Protocol.{module name}.{protocol name} where
module Scribble.Protocol.Arithmetic.MathServer where

-- Hard coded import list
import Scribble.FSM (class Branch, class Initial, class Terminal, class Receive, class Select, class Send, kind Role)
import Type.Row (Cons, Nil)
import Data.Void (Void)

-- Type declaration imports
-- e.g. type <purescript> "Int" from "Prim" as Int;
import Prim (Int)

-- Message data types
-- TODO: Derive JSON encodings for them!
data Add = Add Int Int
data Sum = Sum Int
data Multiply = Add Int Int
data Product = Product Int
data Quit = Quit 

-- Client role definition
foreign import data Client :: Role

-- Client states
foreign import data S6 :: Type
foreign import data S6Add :: Type
foreign import data S6Quit :: Type
foreign import data S6Multiply :: Type
foreign import data S7 :: Type
foreign import data S8 :: Type
foreign import data S9 :: Type

instance initialClient :: Initial Client S6
instance terminalClient :: Terminal Client S7

-- Client state transitions
-- Branch names are the lowercase of the message label expected to receive
instance selectS6 :: Select Server S6 (Cons "quit" S6Quit (Cons "add" S6Add (Cons "multiply" S6Multiply Nil)))
instance sendS6Quit :: Send Server S6Quit S7 Quit
instance sendS6Add :: Send Server S6Add S8 Add
instance sendS6Multiply :: Send Server S6Multiply S9 Multiply
instance receiveS8 :: Receive Server S8 S6 Sum
instance receiveS9 :: Receive Server S9 S6 Product

foreign import data Server :: Role

foreign import data S16 :: Type
foreign import data S16Add :: Type
foreign import data S16Quit :: Type
foreign import data S16Multiply :: Type
foreign import data S17 :: Type
foreign import data S18 :: Type
foreign import data S19 :: Type

instance initialServer :: Initial Server S16
instance terminalServer :: Terminal Server S17

-- This isn't a typo, it should be Branch Server (see the typeclass for more details)
instance branchS16 :: Branch Server S16 (Cons "quit" S16Quit (Cons "add" S16Add (Cons "multiply" S16Multiply Nil)))
instance receiveS16Quit :: Receive Client S16Quit S17 Quit
instance receiveS16Add :: Receive Client S16Add S18 Add
instance receiveS16Multiply :: Receive Client S16Multiply S19 Multiply
instance sendS8 :: Send Server S18 S16 Sum
instance sendS9 :: Send Server S19 S16 Product
