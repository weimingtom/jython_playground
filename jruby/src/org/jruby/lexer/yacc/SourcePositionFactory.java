/***** BEGIN LICENSE BLOCK *****
 * Version: CPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Common Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/cpl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2005 Thomas E Enebo <enebo@acm.org>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the CPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the CPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby.lexer.yacc;

public class SourcePositionFactory implements ISourcePositionFactory
{
	// Position of last token returned.
	private SourcePosition lastPosition = new SourcePosition();
	private LexerSource source;

	public SourcePositionFactory(LexerSource source)
	{
		this.source = source;
	}

	public ISourcePosition getPosition(ISourcePosition startPosition, boolean inclusive)
	{
		int startLine;
		int startOffset;
		int endLine = source.getLine();
		String filename = source.getFilename();

		if (startPosition == null)
		{
			startLine = lastPosition.getEndLine();
			startOffset = lastPosition.getEndOffset();
		} else if (inclusive)
		{
			startLine = startPosition.getStartLine();
			startOffset = startPosition.getStartOffset();
		} else
		{
			startLine = startPosition.getEndLine();
			startOffset = startPosition.getEndOffset();
		}

		lastPosition = new SourcePosition(filename, startLine, endLine, startOffset, source.getOffset());

		return lastPosition;
	}

	public ISourcePosition getUnion(ISourcePosition first, ISourcePosition second)
	{
		return new SourcePosition(first.getFile(), first.getStartLine(), second.getEndLine(), first.getStartOffset(), second.getEndOffset());
	}

	public ISourcePosition getDummyPosition()
	{
		return new SourcePosition("", -1, -1, 0, 0);
	}
}
