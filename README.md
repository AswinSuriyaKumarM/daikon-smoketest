## Daikon workflow

1. Build runtime (once)
2. Compile Java program
3. Run DynComp → create .decls file
4. Run Chicory → create execution trace (.dtrace.gz)
5. Run Daikon → infer invariants
6. Compare outputs

## Execution Steps

### 1. Set Daikon path

export DAIKONDIR=/mnt/c/tools/daikon/daikon-5.8.22

### 2. Build Daikon runtime (only once)

make -C $DAIKONDIR/java dcomp_rt.jar

### 3. Compile Java program

javac -g example.java exampleTest.java

### 4. Run DynComp (create comparability file)

java -cp ".:$DAIKONDIR/daikon.jar" daikon.DynComp exampleTest

Output: exampleTest.decls-DynComp

### 5. Run Chicory (collect execution trace)

java -cp ".:$DAIKONDIR/daikon.jar" daikon.Chicory \
  --comparability-file=exampleTest.decls-DynComp \
  exampleTest

Output: exampleTest.dtrace.gz

### 6. Run Daikon (default configuration)

java -cp "$DAIKONDIR/daikon.jar" daikon.Daikon exampleTest.dtrace.gz > setA.txt

### 7. Run Daikon (filters disabled)

java -cp "$DAIKONDIR/daikon.jar" daikon.Daikon \
  --config_option daikon.inv.filter.DerivedParameterFilter.enabled=false \
  --config_option daikon.inv.filter.DotNetStringFilter.enabled=false \
  --config_option daikon.inv.filter.ObviousFilter.enabled=false \
  --config_option daikon.inv.filter.OnlyConstantVariablesFilter.enabled=false \
  --config_option daikon.inv.filter.ParentFilter.enabled=false \
  --config_option daikon.inv.filter.ReadonlyPrestateFilter.enabled=false \
  --config_option daikon.inv.filter.SimplifyFilter.enabled=false \
  --config_option daikon.inv.filter.UnjustifiedFilter.enabled=false \
  --config_option daikon.inv.filter.UnmodifiedVariableEqualityFilter.enabled=false \
  exampleTest.dtrace.gz > setB.txt

### 8. Compare results

diff -u setA.txt setB.txt > diff.txt

### 9. Identify useful invariants [ChatGPT]

From the comparison (`diff.txt`), the following invariants capture meaningful program behavior:

#### Behavioral invariants

this.count > orig(this.count)

#### Functional invariants

this.count == return
return == orig(this.count)

#### Frame invariants

this.count == orig(this.count)

### Interpretation

* **Behavioral** → shows how the program state changes
* **Functional** → shows relation between return value and state
* **Frame** → shows variables that remain unchanged

### Conclusion

> These invariants represent the most useful information extracted by Daikon, capturing state changes, method behavior, and input–output relationships.