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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.scribble.editor.dsl.scribbleDsl.Argument;
import org.scribble.editor.dsl.scribbleDsl.LocalProtocolBlock;
import org.scribble.editor.dsl.scribbleDsl.LocalProtocolDecl;
import org.scribble.editor.dsl.scribbleDsl.ParameterDecl;
import org.scribble.editor.dsl.scribbleDsl.RoleDecl;
import org.scribble.editor.dsl.scribbleDsl.RoleInstantiation;
import org.scribble.editor.dsl.scribbleDsl.ScribbleDslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Protocol Decl</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalProtocolDeclImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalProtocolDeclImpl#getRole <em>Role</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalProtocolDeclImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalProtocolDeclImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalProtocolDeclImpl#getBlock <em>Block</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalProtocolDeclImpl#getInstantiates <em>Instantiates</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalProtocolDeclImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link org.scribble.editor.dsl.scribbleDsl.impl.LocalProtocolDeclImpl#getRoleInstantiations <em>Role Instantiations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalProtocolDeclImpl extends MinimalEObjectImpl.Container implements LocalProtocolDecl
{
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
   * The default value of the '{@link #getRole() <em>Role</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRole()
   * @generated
   * @ordered
   */
  protected static final String ROLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRole() <em>Role</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRole()
   * @generated
   * @ordered
   */
  protected String role = ROLE_EDEFAULT;

  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<ParameterDecl> parameters;

  /**
   * The cached value of the '{@link #getRoles() <em>Roles</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRoles()
   * @generated
   * @ordered
   */
  protected EList<RoleDecl> roles;

  /**
   * The cached value of the '{@link #getBlock() <em>Block</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBlock()
   * @generated
   * @ordered
   */
  protected LocalProtocolBlock block;

  /**
   * The default value of the '{@link #getInstantiates() <em>Instantiates</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstantiates()
   * @generated
   * @ordered
   */
  protected static final String INSTANTIATES_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInstantiates() <em>Instantiates</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstantiates()
   * @generated
   * @ordered
   */
  protected String instantiates = INSTANTIATES_EDEFAULT;

  /**
   * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArguments()
   * @generated
   * @ordered
   */
  protected EList<Argument> arguments;

  /**
   * The cached value of the '{@link #getRoleInstantiations() <em>Role Instantiations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRoleInstantiations()
   * @generated
   * @ordered
   */
  protected EList<RoleInstantiation> roleInstantiations;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LocalProtocolDeclImpl()
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
    return ScribbleDslPackage.Literals.LOCAL_PROTOCOL_DECL;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRole()
  {
    return role;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRole(String newRole)
  {
    String oldRole = role;
    role = newRole;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE, oldRole, role));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ParameterDecl> getParameters()
  {
    if (parameters == null)
    {
      parameters = new EObjectContainmentEList<ParameterDecl>(ParameterDecl.class, this, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__PARAMETERS);
    }
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<RoleDecl> getRoles()
  {
    if (roles == null)
    {
      roles = new EObjectContainmentEList<RoleDecl>(RoleDecl.class, this, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLES);
    }
    return roles;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalProtocolBlock getBlock()
  {
    return block;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBlock(LocalProtocolBlock newBlock, NotificationChain msgs)
  {
    LocalProtocolBlock oldBlock = block;
    block = newBlock;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK, oldBlock, newBlock);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBlock(LocalProtocolBlock newBlock)
  {
    if (newBlock != block)
    {
      NotificationChain msgs = null;
      if (block != null)
        msgs = ((InternalEObject)block).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK, null, msgs);
      if (newBlock != null)
        msgs = ((InternalEObject)newBlock).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK, null, msgs);
      msgs = basicSetBlock(newBlock, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK, newBlock, newBlock));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getInstantiates()
  {
    return instantiates;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInstantiates(String newInstantiates)
  {
    String oldInstantiates = instantiates;
    instantiates = newInstantiates;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__INSTANTIATES, oldInstantiates, instantiates));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Argument> getArguments()
  {
    if (arguments == null)
    {
      arguments = new EObjectContainmentEList<Argument>(Argument.class, this, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ARGUMENTS);
    }
    return arguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<RoleInstantiation> getRoleInstantiations()
  {
    if (roleInstantiations == null)
    {
      roleInstantiations = new EObjectContainmentEList<RoleInstantiation>(RoleInstantiation.class, this, ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS);
    }
    return roleInstantiations;
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
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__PARAMETERS:
        return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLES:
        return ((InternalEList<?>)getRoles()).basicRemove(otherEnd, msgs);
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK:
        return basicSetBlock(null, msgs);
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ARGUMENTS:
        return ((InternalEList<?>)getArguments()).basicRemove(otherEnd, msgs);
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS:
        return ((InternalEList<?>)getRoleInstantiations()).basicRemove(otherEnd, msgs);
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
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__NAME:
        return getName();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE:
        return getRole();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__PARAMETERS:
        return getParameters();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLES:
        return getRoles();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK:
        return getBlock();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__INSTANTIATES:
        return getInstantiates();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ARGUMENTS:
        return getArguments();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS:
        return getRoleInstantiations();
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
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__NAME:
        setName((String)newValue);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE:
        setRole((String)newValue);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends ParameterDecl>)newValue);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLES:
        getRoles().clear();
        getRoles().addAll((Collection<? extends RoleDecl>)newValue);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK:
        setBlock((LocalProtocolBlock)newValue);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__INSTANTIATES:
        setInstantiates((String)newValue);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ARGUMENTS:
        getArguments().clear();
        getArguments().addAll((Collection<? extends Argument>)newValue);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS:
        getRoleInstantiations().clear();
        getRoleInstantiations().addAll((Collection<? extends RoleInstantiation>)newValue);
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
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__NAME:
        setName(NAME_EDEFAULT);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE:
        setRole(ROLE_EDEFAULT);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__PARAMETERS:
        getParameters().clear();
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLES:
        getRoles().clear();
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK:
        setBlock((LocalProtocolBlock)null);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__INSTANTIATES:
        setInstantiates(INSTANTIATES_EDEFAULT);
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ARGUMENTS:
        getArguments().clear();
        return;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS:
        getRoleInstantiations().clear();
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
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE:
        return ROLE_EDEFAULT == null ? role != null : !ROLE_EDEFAULT.equals(role);
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__PARAMETERS:
        return parameters != null && !parameters.isEmpty();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLES:
        return roles != null && !roles.isEmpty();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__BLOCK:
        return block != null;
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__INSTANTIATES:
        return INSTANTIATES_EDEFAULT == null ? instantiates != null : !INSTANTIATES_EDEFAULT.equals(instantiates);
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ARGUMENTS:
        return arguments != null && !arguments.isEmpty();
      case ScribbleDslPackage.LOCAL_PROTOCOL_DECL__ROLE_INSTANTIATIONS:
        return roleInstantiations != null && !roleInstantiations.isEmpty();
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
    result.append(", role: ");
    result.append(role);
    result.append(", instantiates: ");
    result.append(instantiates);
    result.append(')');
    return result.toString();
  }

} //LocalProtocolDeclImpl
