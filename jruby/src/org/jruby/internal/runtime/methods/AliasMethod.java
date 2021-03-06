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
 * Copyright (C) 2002 Anders Bengtsson <ndrsbngtssn@yahoo.se>
 * Copyright (C) 2002-2004 Jan Arne Petersen <jpetersen@uni-bonn.de>
 * Copyright (C) 2004-2005 Thomas E Enebo <enebo@acm.org>
 * Copyright (C) 2004 Stefan Matthias Aust <sma@3plus4.de>
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
package org.jruby.internal.runtime.methods;

import org.jruby.IRuby;
import org.jruby.RubyModule;
import org.jruby.runtime.ICallable;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * 
 * @author jpetersen
 */
public class AliasMethod extends AbstractMethod
{
	private ICallable oldMethod;
	private String oldName;

	/*
	 * This code used to try and optimize the case of when oldMethod is an
	 * aliasMethod. This seems a little overkill.
	 */
	public AliasMethod(ICallable oldMethod, String oldName)
	{
		super(oldMethod.getImplementationClass(), oldMethod.getVisibility());

		this.oldName = oldName;
		this.oldMethod = oldMethod;
	}

	public String getOriginalName()
	{
		return oldName;
	}

	public void preMethod(IRuby runtime, RubyModule lastClass, IRubyObject recv, String name, IRubyObject[] args, boolean noSuper)
	{
		// FIXME: using implementationClass may not be right, since the alias
		// may be found in an included module
		oldMethod.preMethod(runtime, implementationClass, recv, name, args, noSuper);
	}

	public void postMethod(IRuby runtime)
	{
		oldMethod.postMethod(runtime);
	}

	public IRubyObject internalCall(IRuby runtime, IRubyObject receiver, RubyModule lastClass, String name, IRubyObject[] args, boolean noSuper)
	{
		return oldMethod.internalCall(runtime, receiver, lastClass, oldName, args, noSuper);
	}

	public ICallable dup()
	{
		return new AliasMethod(oldMethod, oldName);
	}
}
