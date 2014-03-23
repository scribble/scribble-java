/**
 */
package org.scribble.editor.dsl.scribbleDsl.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.scribble.editor.dsl.scribbleDsl.GlobalProtocolDecl;
import org.scribble.editor.dsl.scribbleDsl.ImportDecl;
import org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl;
import org.scribble.editor.dsl.scribbleDsl.ModuleDecl;
import org.scribble.editor.dsl.scribbleDsl.PayloadTypeDecl;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Decl</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl#getGlobals <em>Globals</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl#getLocals <em>Locals</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.ModuleDeclImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModuleDeclImpl extends ModuleImpl implements ModuleDecl
{
  /**
   * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImports()
   * @generated
   * @ordered
   */
  protected EList<ImportDecl> imports;

  /**
   * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTypes()
   * @generated
   * @ordered
   */
  protected EList<PayloadTypeDecl> types;

  /**
   * The cached value of the '{@link #getGlobals() <em>Globals</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGlobals()
   * @generated
   * @ordered
   */
  protected EList<GlobalProtocolDecl> globals;

  /**
   * The cached value of the '{@link #getLocals() <em>Locals</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLocals()
   * @generated
   * @ordered
   */
  protected EList<LocalProtocolDecl> locals;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ModuleDeclImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return ScribbleDslPackage.Literals.MODULE_DECL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ImportDecl> getImports()
  {
    if (imports == null)
    {
      imports = new EObjectContainmentEList<ImportDecl>(ImportDecl.class, this, ScribbleDslPackage.MODULE_DECL__IMPORTS);
    }
    return imports;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<PayloadTypeDecl> getTypes()
  {
    if (types == null)
    {
      types = new EObjectContainmentEList<PayloadTypeDecl>(PayloadTypeDecl.class, this, ScribbleDslPackage.MODULE_DECL__TYPES);
    }
    return types;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<GlobalProtocolDecl> getGlobals()
  {
    if (globals == null)
    {
      globals = new EObjectContainmentEList<GlobalProtocolDecl>(GlobalProtocolDecl.class, this, ScribbleDslPackage.MODULE_DECL__GLOBALS);
    }
    return globals;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LocalProtocolDecl> getLocals()
  {
    if (locals == null)
    {
      locals = new EObjectContainmentEList<LocalProtocolDecl>(LocalProtocolDecl.class, this, ScribbleDslPackage.MODULE_DECL__LOCALS);
    }
    return locals;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.MODULE_DECL__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.MODULE_DECL__IMPORTS:
        return ((InternalEList<?>)getImports()).basicRemove(otherEnd, msgs);
      case ScribbleDslPackage.MODULE_DECL__TYPES:
        return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
      case ScribbleDslPackage.MODULE_DECL__GLOBALS:
        return ((InternalEList<?>)getGlobals()).basicRemove(otherEnd, msgs);
      case ScribbleDslPackage.MODULE_DECL__LOCALS:
        return ((InternalEList<?>)getLocals()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.MODULE_DECL__IMPORTS:
        return getImports();
      case ScribbleDslPackage.MODULE_DECL__TYPES:
        return getTypes();
      case ScribbleDslPackage.MODULE_DECL__GLOBALS:
        return getGlobals();
      case ScribbleDslPackage.MODULE_DECL__LOCALS:
        return getLocals();
      case ScribbleDslPackage.MODULE_DECL__NAME:
        return getName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.MODULE_DECL__IMPORTS:
        getImports().clear();
        getImports().addAll((Collection<? extends ImportDecl>)newValue);
        return;
      case ScribbleDslPackage.MODULE_DECL__TYPES:
        getTypes().clear();
        getTypes().addAll((Collection<? extends PayloadTypeDecl>)newValue);
        return;
      case ScribbleDslPackage.MODULE_DECL__GLOBALS:
        getGlobals().clear();
        getGlobals().addAll((Collection<? extends GlobalProtocolDecl>)newValue);
        return;
      case ScribbleDslPackage.MODULE_DECL__LOCALS:
        getLocals().clear();
        getLocals().addAll((Collection<? extends LocalProtocolDecl>)newValue);
        return;
      case ScribbleDslPackage.MODULE_DECL__NAME:
        setName((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.MODULE_DECL__IMPORTS:
        getImports().clear();
        return;
      case ScribbleDslPackage.MODULE_DECL__TYPES:
        getTypes().clear();
        return;
      case ScribbleDslPackage.MODULE_DECL__GLOBALS:
        getGlobals().clear();
        return;
      case ScribbleDslPackage.MODULE_DECL__LOCALS:
        getLocals().clear();
        return;
      case ScribbleDslPackage.MODULE_DECL__NAME:
        setName(NAME_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case ScribbleDslPackage.MODULE_DECL__IMPORTS:
        return imports != null && !imports.isEmpty();
      case ScribbleDslPackage.MODULE_DECL__TYPES:
        return types != null && !types.isEmpty();
      case ScribbleDslPackage.MODULE_DECL__GLOBALS:
        return globals != null && !globals.isEmpty();
      case ScribbleDslPackage.MODULE_DECL__LOCALS:
        return locals != null && !locals.isEmpty();
      case ScribbleDslPackage.MODULE_DECL__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //ModuleDeclImpl
