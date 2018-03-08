package com.github.jlf1997.spring_boot_sdk.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.Assert;

import com.github.jlf1997.spring_boot_sdk.model.BaseModel;
import com.github.jlf1997.spring_boot_sdk.model.TimeEntity;
import com.github.jlf1997.spring_boot_sdk.oper.DBFinder;
import com.github.jlf1997.spring_boot_sdk.oper.SpringDateJpaOper;
import com.github.jlf1997.spring_boot_sdk.util.RefUtil;



public abstract class FindBase<T extends BaseModel,ID extends Serializable>  {
	
	/**
	 * JpaSpecificationExecutor对象
	 */
	public abstract JpaSpecificationExecutor<T> specjpa();
	/**
	 * JpaRepository 对象
	 */
	public abstract JpaRepository<T,ID> jpa();
	
	/**
	 * 追加查询条件
	 */
	public abstract void addWhere(T t,List<Predicate>  predicates,Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
	

	/**
	 * 对查询结果进行处理
	 * @param page
	 */
	public abstract void setSelect(T t);
	
	
	/**
	 * 处理查询出的对象
	 * @param list
	 */
	public  void setSelect(List<T> list) {
		list.forEach(arg0->{
			setSelect(arg0);
		});
	}
	
	public  void setSelect(Page<T> page) {
		setSelect(page.getContent());
	}
	
	/**
	 * 自定义查询条件
	 */
	private  void where(T t,List<Predicate>  predicates,Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(t!=null) {		
			Field[] fields = t.getClass().getDeclaredFields();
			PropertyDescriptor property = null;
			for (Field field : fields) {				
				try {
					property = new PropertyDescriptor(field.getName(), t.getClass());
				} catch (IntrospectionException e) {					
					e.printStackTrace();
				}
				Assert.notNull(property, "property must not be null");
				Method readMethod = RefUtil.getReadMethod(property);				
				if (readMethod != null ) {					
					try {						
						Object value = readMethod.invoke(t);
						if(value!=null) {
							SpringDateJpaOper<T> springDateJpaOper = new SpringDateJpaOper<>(root,query,cb);
							DBFinder dbOper = RefUtil.getAnnotation(field, DBFinder.class);
							if(dbOper!=null && dbOper.added()) {								
								switch(dbOper.opType()) {
								case EQ:
									springDateJpaOper.eq(predicates,field.getName(), value);									
									break;
								case LIKE:	
									springDateJpaOper.like(predicates,field.getName(), value);									
									break;
								case GE :
									springDateJpaOper.ge(predicates,field.getName(), value);								
									break;
								case LE:
									springDateJpaOper.le(predicates,field.getName(), value);									
									break;
								case LT:
									springDateJpaOper.lt(predicates,field.getName(), value);
									break;
								case GT:
									springDateJpaOper.gt(predicates,field.getName(), value);
									break;
								case BIT_EXIST_ANY:
									springDateJpaOper.bitExistAny(predicates,field.getName(), value);
									break;
								case BIT_NOT_EXIST_ALL:
									springDateJpaOper.bitNotExistALL(predicates,field.getName(), value);
									break;
								case BIT_EXIST_ALL:
									springDateJpaOper.bitExistALL(predicates,field.getName(), value);
									break;
								case NOT_EQUAL:
									springDateJpaOper.notEqual(predicates,field.getName(), value);
									break;
								default:
									break;								
								}								
							}else if(dbOper.added()){
								predicates.add(cb.equal(root.get(field.getName()),  value));
							}
						}
					}
					catch (Throwable ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		
	}
	
	
	

	public List<T> findAll(T t, Long creTimeBegin, Long creTimeEnd, Long updTimeBegin, Long updTimeEnd) {	
		return findAll(t,creTimeBegin,creTimeEnd,updTimeBegin,updTimeEnd,(String)null,(Direction)null);
	}
	
	
	public List<T> findAll(T t, TimeEntity createTimeEntity, TimeEntity updTimeEntity) {
		return findAll(t,(SpringDataJpaFinder<T>)null,createTimeEntity,updTimeEntity,(Sort)null);
	}
	
	
	public List<T> findAll(T t, SpringDataJpaFinder<T> sdjFinder,TimeEntity createTimeEntity, TimeEntity updTimeEntity) {
		return findAll(t,sdjFinder,createTimeEntity,updTimeEntity,(Sort)null);
	}
	
	
		
	public Page<T> findAllPage(T t, Long creTimeBegin, Long creTimeEnd, Long updTimeBegin, Long updTimeEnd, String sortStr,
			Direction direction, Integer pageSize, Integer pageIndex) {
		String[] sorStrArray = sortStr.split(",");
		Sort sort = new Sort(direction, sorStrArray);
		PageRequest pageRequest = new PageRequest(pageIndex,pageSize,sort);	
		TimeEntity createTimeEntity = new TimeEntity();
		createTimeEntity.setBegainTime(creTimeBegin); 
		createTimeEntity.setEndTime(creTimeEnd); 
		TimeEntity updTimeEntity = new TimeEntity();
		updTimeEntity.setBegainTime(updTimeBegin); 
		updTimeEntity.setEndTime(updTimeEnd);
		return findAllPage(t, createTimeEntity, updTimeEntity,pageRequest);
	}
	
	
	/**
	 * 分页条件查询
	 * @param t
	 * @param pageRequest
	 * @param createTimeEntity
	 * @param updTimeEntity
	 * @return
	 */
	
	public Page<T> findAllPage(T t,TimeEntity createTimeEntity,TimeEntity updTimeEntity,PageRequest pageRequest) {		
		return findAllPage(t,(SpringDataJpaFinder<T>)null,createTimeEntity,updTimeEntity,pageRequest);
	}
	
	/**
	 * 分页条件查询
	 * @param t
	 * @param sdjFinder 自定义查询对象
	 * @param createTimeEntity
	 * @param updTimeEntity
	 * @param pageRequest
	 * @return
	 */
	public Page<T> findAllPage(T t,SpringDataJpaFinder<T> sdjFinder, TimeEntity createTimeEntity, TimeEntity updTimeEntity, PageRequest pageRequest) {
		Page<T> page =  specjpa().findAll(getSpecification(t,sdjFinder,createTimeEntity,updTimeEntity),pageRequest);	
		setSelect(page);
		return page;
		
	}
	
	
	
	public T find(T t,TimeEntity createTimeEntity,TimeEntity updTimeEntity) {
		List<T> list = specjpa().findAll(getSpecification(t,null,createTimeEntity,updTimeEntity));
		if(list==null || list.size()!=1) {
			return null;
		}else {
			setSelect(list);
			return list.get(0);
		}
	}
	
	
	public T find(T t, Long creTimeBegin, Long creTimeEnd, Long updTimeBegin, Long updTimeEnd, String sortStr,
			Direction direction, Integer pageSize, Integer pageIndex) {
		TimeEntity createTimeEntity = new TimeEntity();
		createTimeEntity.setBegainTime(creTimeBegin); 
		createTimeEntity.setEndTime(creTimeEnd); 
		TimeEntity updTimeEntity = new TimeEntity();
		updTimeEntity.setBegainTime(updTimeBegin); 
		updTimeEntity.setEndTime(updTimeEnd);
		return find(t, createTimeEntity, updTimeEntity);
	}
	
	/**
	 * 保存或更新：根据是否有主键自动判断
	 * @param t
	 * @return
	 */
	@Transactional
	public T save(T t) {
		try {
			return jpa().save(t);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		 
	}
	
	/**
	 * 批量保存或更新，更新失败返回null
	 * @param entities
	 * @return
	 */
	@Transactional
	public List<T> save(Iterable<T> entities) {
		try {
			return jpa().save(entities);
		}catch (Exception e) {
			return null;
		}
		 
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T findOne(ID id) {
		T t =  jpa().findOne(id);
 		if(t!=null && (t.getDeleted()==null  || 1!=t.getDeleted()) ) {
			setSelect(t);
			return t;
		}else {
			return null;
		}
	}
	
	
	
	
	
	
	public List<T> findAll(T t, Long creTimeBegin, Long creTimeEnd, Long updTimeBegin, Long updTimeEnd, String sortStr,
			Direction direction) {
		Sort sort = null;
		if(sortStr==null || direction==null) {
			String[] sorStrArray = sortStr.split(",");
			sort = new Sort(direction, sorStrArray);
		}			
		TimeEntity createTimeEntity = new TimeEntity();
		createTimeEntity.setBegainTime(creTimeBegin); 
		createTimeEntity.setEndTime(creTimeEnd); 
		TimeEntity updTimeEntity = new TimeEntity();
		updTimeEntity.setBegainTime(updTimeBegin); 
		updTimeEntity.setEndTime(updTimeEnd);
		return findAll(t,(SpringDataJpaFinder<T>)null, createTimeEntity, updTimeEntity,sort);
	}
	/**
	 * 条件查询所有属性
	 * @param t
	 * @param createTimeEntity
	 * @param updTimeEntity
	 * @return
	 */
	public List<T> findAll(T t,SpringDataJpaFinder<T> sdjFinder,TimeEntity createTimeEntity,TimeEntity updTimeEntity,Sort sort){		
		List<T> list =  specjpa().findAll(getSpecification(t,sdjFinder,createTimeEntity,updTimeEntity),sort);
		setSelect(list);
		return list;
	}
	
	/**
	 * 删除
	 * @param t
	 * @return
	 */
	@Transactional
	public List<T> delete(T t) {
		List<T> list = findAll(t,null,null);
		list.stream().forEach(action->{
			action.setDeleted(1);
		});		
		return jpa().save(list);
	}
	
	/**
	 * 更新对应字段，val为空则更新所有字段
	 * @param t 需要更新的值
	 * @param id 主键id
	 * @param val 需要更新的值
	 * @return
	 */
	@Transactional
	public T updateIncludeValue(T t,ID id,String... val) {
		
			if(val==null) {
				return jpa().save(t);
			}else {			
				T db = findOne(id);
				if(db==null) {
					return null;
				}
				setDbValue(t,db,t.getClass(),val);
				return save(db);
			}	
	}
	
	/**
	 * 递归获取父类属性值，并比较其中是否存在需要更新字段，有则更新对应字段值
	 * @param t
	 * @param db
	 * @param classZ
	 * @param val
	 */
	private void setDbValue(T t,T db,Class<?> classZ,String... val) {
		Field[] fields = classZ.getDeclaredFields();
		setValue(t,db,fields,t.getClass(),val);
		Class<?> sup = classZ.getSuperclass();
		if(sup.equals(Object.class)) {
			System.out.println("out");
		}else {
			setDbValue(t,db,sup,val);
		}
	}
		
	
	/**
	 * 设置属性 值
	 * @param t
	 * @param db
	 * @param fields
	 * @param classZ
	 * @param val
	 */
	private void setValue(T t,T db,Field[] fields,Class<?> classZ,String... val) {
		 Field.setAccessible(fields,   true); 
		for(Field field: fields) {
			String name = field.getName();
			if(nameInStrs(name,val)) {
				Object obj = getFieldValueByName(name,t,classZ);
//				if(obj!=null)
				{
					try {
						field.set(db, obj);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}	
	}
	
	
	/**
	 * 保存所有非空的值，null值不更新
	 * @param t
	 * @param db
	 * @param fields
	 * @param classZ
	 */
	@SuppressWarnings("unused")
	private void setValue(T t,T db,Field[] fields,Class<?> classZ) {
		 Field.setAccessible(fields,   true); 
		for(Field field: fields) {
			String name = field.getName();		 
			Object obj = getFieldValueByName(name,t,classZ);
			if(obj!=null){
				try {
					field.set(db, obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}					
		}	
	}
	
	/**
	 * 判断属性值是否在数组中
	 * @param name
	 * @param val
	 * @return
	 */
	private boolean nameInStrs(String name ,String... val) {
		if(name==null||val==null) {
			return false;
		}
		for(String str:val) {
			if(name.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取属性名称对应的值，通过get方法
	 * @param fieldName
	 * @param t
	 * @param classZ
	 * @return
	 */
	  private Object getFieldValueByName(String fieldName, T t,Class<?> classZ) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = classZ.getMethod(getter, new Class[] {});    
	           Object value = method.invoke(t, new Object[] {});    
	           return value;    
	       } catch (Exception e) {   
	          e.printStackTrace();
	           return null;    
	       }    
	   }  
	  
	/**
	 * 构造查询条件
	 * @param t
	 * @param cretTimeEntity
	 * @param updTimeEntity
	 * @return
	 */
	 private  Specification<T> getSpecification(T t,SpringDataJpaFinder<T> sdjFinder,TimeEntity cretTimeEntity,TimeEntity updTimeEntity){
			return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate>  predicates = getPredicates(root, query, cb,cretTimeEntity,updTimeEntity);
				if(t!=null) {
					if(sdjFinder!=null) {
						sdjFinder.where(t,predicates,root, query, cb);
					}else {
						where(t,predicates,root,query,cb);
					}
				}
				//追加查询
				addWhere(t,predicates,root,query,cb);
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	/**
	 * 默认查询条件
	 * @param root
	 * @param query
	 * @param cb
	 * @param createTimeEntity
	 * @param updTimeEntity
	 * @return
	 */
	private List<Predicate> getPredicates(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb,TimeEntity createTimeEntity,TimeEntity updTimeEntity) {
		List<Predicate>  predicates = new ArrayList<>();		
		//createTimeb
		if(createTimeEntity!=null && createTimeEntity.getBegainTime() !=null ) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("creTime"), new Date(createTimeEntity.getBegainTime())));
		}
		//createTimee
		if(createTimeEntity!=null && createTimeEntity.getEndTime() !=null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("creTime"), new Date(createTimeEntity.getEndTime())));
		}
		//updTimeb
		if(updTimeEntity!=null && updTimeEntity.getBegainTime() !=null ) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("updTime"), new Date(updTimeEntity.getBegainTime())));
		}
		//updTimee
		if(updTimeEntity!=null && updTimeEntity.getEndTime() !=null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("updTime"), new Date(updTimeEntity.getEndTime())));
		}	
		//删除标识
		predicates.add(cb.or(cb.equal(root.get("deleted"), 0),cb.isNull(root.get("deleted"))));
		return predicates;
		}
	
	
	
}
