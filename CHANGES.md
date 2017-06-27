Scribble 0.4
---

- Updated scribblec script interface: check

    ./scribblec --help
    
  for new usages.  
  
- The core tooling has been refactored to support an alternative validation 
  method.  To obtain more details on safety/progress errors, use the -V 
  command line flag.

  N.B. the -oldwf command line flag allows to use only the well-formedness
  from the previous version of the tool.  Warning: no guarantees are offered
  when validating newer Scribble features using -oldwf.
  
- Most Scribble demos that were previously in 

    modules/core/src/test/scrib/demo
  
  have been relocated to 
 
    modules/demos/scrib/
    
  Some demo files have also been renamed or repackaged.
   