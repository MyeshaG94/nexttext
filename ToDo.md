Feel free to comment on any of these or add some of own.

  * Implement a PStyleProperty to replace the stroke, stroke color, color, ... properties.
    * This will make setting the PApplet properties easier when rendering glyphs and will avoid having to go back and forth between the Java2D and Processing constants for things like stroke caps and line joins.

  * Add the website to the repository.

  * Add to the behaviour factories to simplify applying behaviours to objects.

  * Fix the tutorial applet as it seem to crash the browser over time (OSX only?).

  * Create diagrams for the tutorial, mainly to show the behaviour tree.

  * Throw warnings when appropriate. (e.g. When removeGlyphBehaviour is called with a behaviour that was added with addGroupBehaviour).

  * Redo the tutorial's nomenclature (see Jason's example in Google Wave).

  * Create a StayInRect behaviour, which StayInWindow should extend from.

  * Think about using proDoc instead of javaDoc.

  * Change glyph/word/group to glyph/word/phrase (maybe).

  * Make setting up collision between objects easier.