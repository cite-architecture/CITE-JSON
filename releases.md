# citejson: release notes

**2.10.0**: Updated versions of Circe dependencies.

**2.9.0**: OHCO2 10.12.5 and xCite 4.0.2.

**2.8.0**: Reworked `build.sbt`. Updated many dependencies.

**2.7.0**:  Added parsing for `CiteTriple`, `Vector[CiteTriple]`, and `Option[Vector[CiteTriple]]` possibly attached to a `CorpusJson`.

**2.6.0**:  Added parsing for optional DSE map attached to a Vector of CiteObjects or Corpus.

**2.5.0**:  Added parsing for optional stats map attached to a Vector of CiteObjects

**2.4.0**:  Added parsing DseRecord and Vector[DseRecord]

**2.3.0**:  Added parsing for a `Vector[Cite2Urn]`.

**2.2.0**:  Added parsing for a `cite2UrnString`.

**2.1.0**:  Added parsing for a `labelMap`, `Map[Cite2Urn,String]`.

**2.0.0**:  Breaking change: ctsUrnStr now returns an Option[CtsUrn]  

**1.1.4**:  Added method for parsing a single CtsUrnString.

**1.1.3**:  Moved method for parsing a JSON expression of a Cite Library's metadata (urn, name, license) into CiteLibraryJson class.

**1.1.2**:  Added method for parsing a JSON expression of a Cite Library's metadata (urn, name, license).

**1.1.1**:  update dependencies

**1.1.0**:  update dependencies, add skelly-compliant cross-building functionality

**1.0.0**:  first full release

**0.1.0**:  initial development
